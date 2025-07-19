package com.hayuwidyas.data.repository

import com.hayuwidyas.data.api.WooCommerceService
import com.hayuwidyas.data.local.dao.ProductDao
import com.hayuwidyas.data.local.entity.ProductEntity
import com.hayuwidyas.data.model.CategoryDto
import com.hayuwidyas.data.model.ProductDto
import com.hayuwidyas.domain.model.Category
import com.hayuwidyas.domain.model.Product
import com.hayuwidyas.domain.model.Review
import com.hayuwidyas.domain.repository.ProductRepository
import com.hayuwidyas.util.DummyData
import com.hayuwidyas.util.DummyData.toDomainModel
import com.hayuwidyas.util.NetworkResult
import com.hayuwidyas.util.Resource
import com.hayuwidyas.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Product Repository Implementation
 * 
 * Implements ProductRepository interface with proper caching strategy.
 * Uses WooCommerce API as primary source and Room database for caching.
 * Falls back to dummy data when API is unavailable.
 */
@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val wooCommerceService: WooCommerceService,
    private val productDao: ProductDao
) : ProductRepository {

    companion object {
        private const val CACHE_TIMEOUT = 30 * 60 * 1000L // 30 minutes
    }

    override suspend fun getProducts(
        page: Int,
        perPage: Int,
        search: String?,
        category: String?,
        minPrice: Double?,
        maxPrice: Double?,
        sortBy: String,
        sortOrder: String,
        featured: Boolean?,
        onSale: Boolean?,
        forceRefresh: Boolean
    ): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            // Check cache first if not forcing refresh
            if (!forceRefresh) {
                val cachedProducts = when {
                    search != null -> productDao.searchProducts(search).map { entities ->
                        entities.map { it.toDomainModel() }
                    }
                    category != null -> productDao.getProductsByCategory(category).map { entities ->
                        entities.map { it.toDomainModel() }
                    }
                    featured == true -> productDao.getFeaturedProducts().map { entities ->
                        entities.map { it.toDomainModel() }
                    }
                    onSale == true -> productDao.getProductsOnSale().map { entities ->
                        entities.map { it.toDomainModel() }
                    }
                    else -> {
                        when (sortBy) {
                            "price" -> if (sortOrder == "asc") {
                                productDao.getProductsSortedByPriceLowToHigh()
                            } else {
                                productDao.getProductsSortedByPriceHighToLow()
                            }.map { entities -> entities.map { it.toDomainModel() } }
                            "rating" -> productDao.getProductsSortedByRating().map { entities ->
                                entities.map { it.toDomainModel() }
                            }
                            "popularity" -> productDao.getProductsSortedByPopularity().map { entities ->
                                entities.map { it.toDomainModel() }
                            }
                            else -> productDao.getAllProducts().map { entities ->
                                entities.map { it.toDomainModel() }
                            }
                        }
                    }
                }

                // Emit cached data first
                cachedProducts.collect { products ->
                    if (products.isNotEmpty()) {
                        emit(Resource.Success(applyFilters(products, minPrice, maxPrice)))
                    }
                }
            }

            // Fetch from API
            val apiResult = safeApiCall {
                wooCommerceService.getProducts(
                    page = page,
                    perPage = perPage,
                    search = search,
                    category = category,
                    minPrice = minPrice?.toString(),
                    maxPrice = maxPrice?.toString(),
                    orderBy = sortBy,
                    order = sortOrder,
                    featured = featured,
                    onSale = onSale
                )
            }

            when (apiResult) {
                is NetworkResult.Success -> {
                    if (apiResult.data.isSuccessful) {
                        val productDtos = apiResult.data.body() ?: emptyList()
                        val products = productDtos.map { it.toDomainModel() }
                        
                        // Cache products
                        val productEntities = products.map { ProductEntity.fromDomainModel(it) }
                        productDao.insertProducts(productEntities)
                        
                        emit(Resource.Success(products))
                        Timber.d("Fetched ${products.size} products from API")
                    } else {
                        Timber.e("API Error: ${apiResult.data.code()} - ${apiResult.data.message()}")
                        emit(Resource.Error("Failed to fetch products: ${apiResult.data.message()}"))
                    }
                }
                is NetworkResult.Error -> {
                    Timber.e("Network Error: ${apiResult.message}")
                    // Try to get cached data or fallback to dummy data
                    val cachedCount = productDao.getProductCount()
                    if (cachedCount == 0) {
                        // Use dummy data as fallback
                        val dummyProducts = DummyData.getDummyProducts().map { it.toDomainModel() }
                        val filteredProducts = applyFilters(dummyProducts, minPrice, maxPrice)
                        emit(Resource.Success(filteredProducts))
                        Timber.d("Using dummy data: ${filteredProducts.size} products")
                    } else {
                        emit(Resource.Error(apiResult.message))
                    }
                }
                is NetworkResult.Exception -> {
                    Timber.e(apiResult.exception, "API Exception")
                    // Fallback to dummy data
                    val dummyProducts = DummyData.getDummyProducts().map { it.toDomainModel() }
                    val filteredProducts = applyFilters(dummyProducts, minPrice, maxPrice)
                    emit(Resource.Success(filteredProducts))
                    Timber.d("Using dummy data due to exception: ${filteredProducts.size} products")
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error in getProducts")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getProduct(
        productId: Int,
        forceRefresh: Boolean
    ): Flow<Resource<Product>> = flow {
        try {
            emit(Resource.Loading())

            // Check cache first
            if (!forceRefresh) {
                val cachedProduct = productDao.getProductById(productId)
                if (cachedProduct != null) {
                    emit(Resource.Success(cachedProduct.toDomainModel()))
                }
            }

            // Fetch from API
            val apiResult = safeApiCall {
                wooCommerceService.getProduct(productId)
            }

            when (apiResult) {
                is NetworkResult.Success -> {
                    if (apiResult.data.isSuccessful) {
                        val productDto = apiResult.data.body()
                        if (productDto != null) {
                            val product = productDto.toDomainModel()
                            
                            // Cache product
                            productDao.insertProduct(ProductEntity.fromDomainModel(product))
                            
                            emit(Resource.Success(product))
                            Timber.d("Fetched product $productId from API")
                        } else {
                            emit(Resource.Error("Product not found"))
                        }
                    } else {
                        emit(Resource.Error("Failed to fetch product: ${apiResult.data.message()}"))
                    }
                }
                is NetworkResult.Error -> {
                    // Try dummy data
                    val dummyProduct = DummyData.getDummyProducts()
                        .find { it.id == productId }
                        ?.toDomainModel()
                    
                    if (dummyProduct != null) {
                        emit(Resource.Success(dummyProduct))
                        Timber.d("Using dummy data for product $productId")
                    } else {
                        emit(Resource.Error(apiResult.message))
                    }
                }
                is NetworkResult.Exception -> {
                    // Try dummy data
                    val dummyProduct = DummyData.getDummyProducts()
                        .find { it.id == productId }
                        ?.toDomainModel()
                    
                    if (dummyProduct != null) {
                        emit(Resource.Success(dummyProduct))
                        Timber.d("Using dummy data for product $productId due to exception")
                    } else {
                        emit(Resource.Error(apiResult.exception.message ?: "Unknown error"))
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error in getProduct")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getFeaturedProducts(
        forceRefresh: Boolean
    ): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            // Check cache first
            if (!forceRefresh) {
                productDao.getFeaturedProducts().collect { entities ->
                    if (entities.isNotEmpty()) {
                        val products = entities.map { it.toDomainModel() }
                        emit(Resource.Success(products))
                    }
                }
            }

            // Fetch from API
            val apiResult = safeApiCall {
                wooCommerceService.getProducts(featured = true, perPage = 20)
            }

            when (apiResult) {
                is NetworkResult.Success -> {
                    if (apiResult.data.isSuccessful) {
                        val productDtos = apiResult.data.body() ?: emptyList()
                        val products = productDtos.map { it.toDomainModel() }
                        
                        // Cache products
                        val productEntities = products.map { ProductEntity.fromDomainModel(it) }
                        productDao.insertProducts(productEntities)
                        
                        emit(Resource.Success(products))
                        Timber.d("Fetched ${products.size} featured products from API")
                    } else {
                        emit(Resource.Error("Failed to fetch featured products"))
                    }
                }
                is NetworkResult.Error -> {
                    // Use dummy data
                    val dummyProducts = DummyData.getDummyProducts()
                        .filter { it.featured }
                        .map { it.toDomainModel() }
                    emit(Resource.Success(dummyProducts))
                    Timber.d("Using dummy featured products")
                }
                is NetworkResult.Exception -> {
                    // Use dummy data
                    val dummyProducts = DummyData.getDummyProducts()
                        .filter { it.featured }
                        .map { it.toDomainModel() }
                    emit(Resource.Success(dummyProducts))
                    Timber.d("Using dummy featured products due to exception")
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error in getFeaturedProducts")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getProductsOnSale(
        forceRefresh: Boolean
    ): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            // Check cache first
            if (!forceRefresh) {
                productDao.getProductsOnSale().collect { entities ->
                    if (entities.isNotEmpty()) {
                        val products = entities.map { it.toDomainModel() }
                        emit(Resource.Success(products))
                    }
                }
            }

            // Fetch from API
            val apiResult = safeApiCall {
                wooCommerceService.getProducts(onSale = true, perPage = 20)
            }

            when (apiResult) {
                is NetworkResult.Success -> {
                    if (apiResult.data.isSuccessful) {
                        val productDtos = apiResult.data.body() ?: emptyList()
                        val products = productDtos.map { it.toDomainModel() }
                        
                        // Cache products
                        val productEntities = products.map { ProductEntity.fromDomainModel(it) }
                        productDao.insertProducts(productEntities)
                        
                        emit(Resource.Success(products))
                        Timber.d("Fetched ${products.size} sale products from API")
                    } else {
                        emit(Resource.Error("Failed to fetch sale products"))
                    }
                }
                is NetworkResult.Error -> {
                    // Use dummy data
                    val dummyProducts = DummyData.getDummyProducts()
                        .filter { it.onSale }
                        .map { it.toDomainModel() }
                    emit(Resource.Success(dummyProducts))
                    Timber.d("Using dummy sale products")
                }
                is NetworkResult.Exception -> {
                    // Use dummy data
                    val dummyProducts = DummyData.getDummyProducts()
                        .filter { it.onSale }
                        .map { it.toDomainModel() }
                    emit(Resource.Success(dummyProducts))
                    Timber.d("Using dummy sale products due to exception")
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error in getProductsOnSale")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun searchProducts(
        query: String,
        page: Int,
        perPage: Int
    ): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            // Search in cache first
            productDao.searchProducts(query).collect { entities ->
                if (entities.isNotEmpty()) {
                    val products = entities.map { it.toDomainModel() }
                    emit(Resource.Success(products))
                }
            }

            // Search via API
            val apiResult = safeApiCall {
                wooCommerceService.getProducts(
                    search = query,
                    page = page,
                    perPage = perPage
                )
            }

            when (apiResult) {
                is NetworkResult.Success -> {
                    if (apiResult.data.isSuccessful) {
                        val productDtos = apiResult.data.body() ?: emptyList()
                        val products = productDtos.map { it.toDomainModel() }
                        
                        // Cache products
                        val productEntities = products.map { ProductEntity.fromDomainModel(it) }
                        productDao.insertProducts(productEntities)
                        
                        emit(Resource.Success(products))
                        Timber.d("Search found ${products.size} products for query: $query")
                    } else {
                        emit(Resource.Error("Search failed"))
                    }
                }
                is NetworkResult.Error -> {
                    // Search in dummy data
                    val dummyProducts = DummyData.getDummyProducts()
                        .filter { 
                            it.name.contains(query, ignoreCase = true) ||
                            it.description.contains(query, ignoreCase = true) ||
                            it.shortDescription.contains(query, ignoreCase = true)
                        }
                        .map { it.toDomainModel() }
                    emit(Resource.Success(dummyProducts))
                    Timber.d("Search in dummy data found ${dummyProducts.size} products")
                }
                is NetworkResult.Exception -> {
                    // Search in dummy data
                    val dummyProducts = DummyData.getDummyProducts()
                        .filter { 
                            it.name.contains(query, ignoreCase = true) ||
                            it.description.contains(query, ignoreCase = true) ||
                            it.shortDescription.contains(query, ignoreCase = true)
                        }
                        .map { it.toDomainModel() }
                    emit(Resource.Success(dummyProducts))
                    Timber.d("Search in dummy data found ${dummyProducts.size} products due to exception")
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error in searchProducts")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getProductsByCategory(
        category: String,
        page: Int,
        perPage: Int,
        forceRefresh: Boolean
    ): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            // Check cache first
            if (!forceRefresh) {
                productDao.getProductsByCategory(category).collect { entities ->
                    if (entities.isNotEmpty()) {
                        val products = entities.map { it.toDomainModel() }
                        emit(Resource.Success(products))
                    }
                }
            }

            // Fetch from API
            val apiResult = safeApiCall {
                wooCommerceService.getProducts(
                    category = category,
                    page = page,
                    perPage = perPage
                )
            }

            when (apiResult) {
                is NetworkResult.Success -> {
                    if (apiResult.data.isSuccessful) {
                        val productDtos = apiResult.data.body() ?: emptyList()
                        val products = productDtos.map { it.toDomainModel() }
                        
                        // Cache products
                        val productEntities = products.map { ProductEntity.fromDomainModel(it) }
                        productDao.insertProducts(productEntities)
                        
                        emit(Resource.Success(products))
                        Timber.d("Fetched ${products.size} products for category: $category")
                    } else {
                        emit(Resource.Error("Failed to fetch products for category"))
                    }
                }
                is NetworkResult.Error -> {
                    // Filter dummy data by category
                    val dummyProducts = DummyData.getDummyProducts()
                        .filter { productDto ->
                            productDto.categories.any { it.name.contains(category, ignoreCase = true) }
                        }
                        .map { it.toDomainModel() }
                    emit(Resource.Success(dummyProducts))
                    Timber.d("Using dummy data for category: $category")
                }
                is NetworkResult.Exception -> {
                    // Filter dummy data by category
                    val dummyProducts = DummyData.getDummyProducts()
                        .filter { productDto ->
                            productDto.categories.any { it.name.contains(category, ignoreCase = true) }
                        }
                        .map { it.toDomainModel() }
                    emit(Resource.Success(dummyProducts))
                    Timber.d("Using dummy data for category: $category due to exception")
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error in getProductsByCategory")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getProductsByIds(
        productIds: List<Int>,
        forceRefresh: Boolean
    ): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            // Check cache first
            if (!forceRefresh) {
                val cachedProducts = productDao.getProductsByIds(productIds)
                if (cachedProducts.isNotEmpty()) {
                    val products = cachedProducts.map { it.toDomainModel() }
                    emit(Resource.Success(products))
                }
            }

            // Fetch missing products from API
            val products = mutableListOf<Product>()
            
            for (productId in productIds) {
                val apiResult = safeApiCall {
                    wooCommerceService.getProduct(productId)
                }

                when (apiResult) {
                    is NetworkResult.Success -> {
                        if (apiResult.data.isSuccessful) {
                            val productDto = apiResult.data.body()
                            if (productDto != null) {
                                val product = productDto.toDomainModel()
                                products.add(product)
                                
                                // Cache product
                                productDao.insertProduct(ProductEntity.fromDomainModel(product))
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        // Try dummy data
                        val dummyProduct = DummyData.getDummyProducts()
                            .find { it.id == productId }
                            ?.toDomainModel()
                        if (dummyProduct != null) {
                            products.add(dummyProduct)
                        }
                    }
                    is NetworkResult.Exception -> {
                        // Try dummy data
                        val dummyProduct = DummyData.getDummyProducts()
                            .find { it.id == productId }
                            ?.toDomainModel()
                        if (dummyProduct != null) {
                            products.add(dummyProduct)
                        }
                    }
                }
            }

            emit(Resource.Success(products))
            Timber.d("Fetched ${products.size} products by IDs")
        } catch (e: Exception) {
            Timber.e(e, "Error in getProductsByIds")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getRelatedProducts(
        productId: Int,
        limit: Int
    ): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            // Get the main product first to find related products
            val mainProduct = productDao.getProductById(productId)
            if (mainProduct != null) {
                // Find products in same categories
                val relatedProducts = mutableListOf<ProductEntity>()
                
                for (category in mainProduct.categories) {
                    val categoryProducts = productDao.getProductsByCategory(category)
                        .map { entities -> entities.filter { it.id != productId } }
                    
                    categoryProducts.collect { entities ->
                        relatedProducts.addAll(entities.take(limit))
                    }
                    
                    if (relatedProducts.size >= limit) break
                }

                val products = relatedProducts.distinctBy { it.id }
                    .take(limit)
                    .map { it.toDomainModel() }
                
                emit(Resource.Success(products))
                Timber.d("Found ${products.size} related products for product $productId")
            } else {
                // Use dummy data
                val dummyProducts = DummyData.getDummyProducts()
                    .filter { it.id != productId }
                    .take(limit)
                    .map { it.toDomainModel() }
                
                emit(Resource.Success(dummyProducts))
                Timber.d("Using dummy related products")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error in getRelatedProducts")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getCategories(
        forceRefresh: Boolean
    ): Flow<Resource<List<Category>>> = flow {
        try {
            emit(Resource.Loading())

            // Fetch from API
            val apiResult = safeApiCall {
                wooCommerceService.getCategories()
            }

            when (apiResult) {
                is NetworkResult.Success -> {
                    if (apiResult.data.isSuccessful) {
                        val categoryDtos = apiResult.data.body() ?: emptyList()
                        val categories = categoryDtos.map { it.toDomainModel() }
                        emit(Resource.Success(categories))
                        Timber.d("Fetched ${categories.size} categories from API")
                    } else {
                        emit(Resource.Error("Failed to fetch categories"))
                    }
                }
                is NetworkResult.Error -> {
                    // Use dummy categories
                    val dummyCategories = DummyData.getDummyCategories().map { it.toDomainModel() }
                    emit(Resource.Success(dummyCategories))
                    Timber.d("Using dummy categories")
                }
                is NetworkResult.Exception -> {
                    // Use dummy categories
                    val dummyCategories = DummyData.getDummyCategories().map { it.toDomainModel() }
                    emit(Resource.Success(dummyCategories))
                    Timber.d("Using dummy categories due to exception")
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error in getCategories")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getCategory(
        categoryId: Int,
        forceRefresh: Boolean
    ): Flow<Resource<Category>> = flow {
        try {
            emit(Resource.Loading())

            val apiResult = safeApiCall {
                wooCommerceService.getCategory(categoryId)
            }

            when (apiResult) {
                is NetworkResult.Success -> {
                    if (apiResult.data.isSuccessful) {
                        val categoryDto = apiResult.data.body()
                        if (categoryDto != null) {
                            val category = categoryDto.toDomainModel()
                            emit(Resource.Success(category))
                            Timber.d("Fetched category $categoryId from API")
                        } else {
                            emit(Resource.Error("Category not found"))
                        }
                    } else {
                        emit(Resource.Error("Failed to fetch category"))
                    }
                }
                is NetworkResult.Error -> {
                    // Try dummy data
                    val dummyCategory = DummyData.getDummyCategories()
                        .find { it.id == categoryId }
                        ?.toDomainModel()
                    
                    if (dummyCategory != null) {
                        emit(Resource.Success(dummyCategory))
                        Timber.d("Using dummy category $categoryId")
                    } else {
                        emit(Resource.Error(apiResult.message))
                    }
                }
                is NetworkResult.Exception -> {
                    // Try dummy data
                    val dummyCategory = DummyData.getDummyCategories()
                        .find { it.id == categoryId }
                        ?.toDomainModel()
                    
                    if (dummyCategory != null) {
                        emit(Resource.Success(dummyCategory))
                        Timber.d("Using dummy category $categoryId due to exception")
                    } else {
                        emit(Resource.Error(apiResult.exception.message ?: "Unknown error"))
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error in getCategory")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getProductReviews(
        productId: Int,
        page: Int,
        perPage: Int
    ): Flow<Resource<List<Review>>> = flow {
        try {
            emit(Resource.Loading())

            val apiResult = safeApiCall {
                wooCommerceService.getProductReviews(productId, page, perPage)
            }

            when (apiResult) {
                is NetworkResult.Success -> {
                    if (apiResult.data.isSuccessful) {
                        val reviewDtos = apiResult.data.body() ?: emptyList()
                        val reviews = reviewDtos.map { it.toDomainModel() }
                        emit(Resource.Success(reviews))
                        Timber.d("Fetched ${reviews.size} reviews for product $productId")
                    } else {
                        emit(Resource.Error("Failed to fetch reviews"))
                    }
                }
                is NetworkResult.Error -> {
                    emit(Resource.Success(emptyList<Review>()))
                    Timber.d("No reviews available for product $productId")
                }
                is NetworkResult.Exception -> {
                    emit(Resource.Success(emptyList<Review>()))
                    Timber.d("No reviews available for product $productId due to exception")
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error in getProductReviews")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun clearProductCache() {
        try {
            productDao.deleteAllProducts()
            Timber.d("Product cache cleared")
        } catch (e: Exception) {
            Timber.e(e, "Error clearing product cache")
        }
    }

    override suspend fun refreshProductCache() {
        try {
            // Delete old products (older than cache timeout)
            val cutoffTime = System.currentTimeMillis() - CACHE_TIMEOUT
            productDao.deleteOldProducts(cutoffTime)
            Timber.d("Product cache refreshed")
        } catch (e: Exception) {
            Timber.e(e, "Error refreshing product cache")
        }
    }

    override suspend fun isProductCached(productId: Int): Boolean {
        return try {
            productDao.productExists(productId)
        } catch (e: Exception) {
            Timber.e(e, "Error checking if product is cached")
            false
        }
    }

    override suspend fun getCachedProductsCount(): Int {
        return try {
            productDao.getProductCount()
        } catch (e: Exception) {
            Timber.e(e, "Error getting cached products count")
            0
        }
    }

    // Helper functions
    private fun applyFilters(
        products: List<Product>,
        minPrice: Double?,
        maxPrice: Double?
    ): List<Product> {
        var filteredProducts = products

        if (minPrice != null) {
            filteredProducts = filteredProducts.filter { it.price >= minPrice }
        }

        if (maxPrice != null) {
            filteredProducts = filteredProducts.filter { it.price <= maxPrice }
        }

        return filteredProducts
    }

    // Extension functions for DTO to Domain conversion
    private fun ProductDto.toDomainModel(): Product {
        return DummyData.run { this@toDomainModel.toDomainModel() }
    }

    private fun CategoryDto.toDomainModel(): Category {
        return Category(
            id = this.id,
            name = this.name,
            slug = this.slug
        )
    }

    private fun com.hayuwidyas.data.api.ReviewDto.toDomainModel(): Review {
        return Review(
            id = this.id,
            productId = this.productId,
            reviewer = this.name,
            email = this.email,
            review = this.review,
            rating = this.rating,
            dateCreated = this.dateCreated,
            verified = this.verified,
            avatarUrl = this.reviewerAvatarUrls["96"] ?: ""
        )
    }
}
