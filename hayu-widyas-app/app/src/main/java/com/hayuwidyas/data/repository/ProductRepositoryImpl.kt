package com.hayuwidyas.data.repository

import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.data.api.WooCommerceApiService
import com.hayuwidyas.data.local.ProductCacheDao
import com.hayuwidyas.data.local.ProductCacheEntity
import com.hayuwidyas.data.model.Product
import com.hayuwidyas.data.model.ProductCategory
import com.hayuwidyas.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of ProductRepository
 * Handles product data from both remote API and local cache
 */
@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val apiService: WooCommerceApiService,
    private val productCacheDao: ProductCacheDao
) : ProductRepository {
    
    override suspend fun getProducts(
        page: Int,
        perPage: Int,
        orderBy: String,
        order: String,
        category: String?,
        minPrice: String?,
        maxPrice: String?,
        search: String?,
        featured: Boolean?
    ): Flow<ApiResponse<List<Product>>> = flow {
        emit(ApiResponse.Loading())
        
        try {
            val response = apiService.getProducts(
                page = page,
                perPage = perPage,
                orderBy = orderBy,
                order = order,
                category = category,
                minPrice = minPrice,
                maxPrice = maxPrice,
                search = search,
                featured = featured
            )
            
            if (response.isSuccessful) {
                val products = response.body() ?: emptyList()
                
                // Cache products locally
                val cacheEntities = products.map { product ->
                    ProductCacheEntity(
                        id = product.id,
                        name = product.name,
                        slug = product.slug,
                        permalink = product.permalink,
                        dateCreated = product.dateCreated,
                        type = product.type,
                        status = product.status,
                        featured = product.featured,
                        description = product.description,
                        shortDescription = product.shortDescription,
                        sku = product.sku,
                        price = product.price,
                        regularPrice = product.regularPrice,
                        salePrice = product.salePrice,
                        onSale = product.onSale,
                        stockQuantity = product.stockQuantity,
                        stockStatus = product.stockStatus,
                        averageRating = product.averageRating,
                        ratingCount = product.ratingCount,
                        categories = product.categories.map { it.name },
                        images = product.images.map { it.src },
                        variations = product.variations
                    )
                }
                productCacheDao.insertProducts(cacheEntities)
                
                emit(ApiResponse.Success(products))
            } else {
                emit(ApiResponse.Error("Failed to fetch products: ${response.message()}"))
            }
        } catch (e: HttpException) {
            emit(ApiResponse.Error("Network error: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(ApiResponse.Error("Connection error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Unknown error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getProduct(productId: Int): Flow<ApiResponse<Product>> = flow {
        emit(ApiResponse.Loading())
        
        try {
            val response = apiService.getProduct(productId)
            
            if (response.isSuccessful) {
                val product = response.body()
                if (product != null) {
                    // Cache product locally
                    val cacheEntity = ProductCacheEntity(
                        id = product.id,
                        name = product.name,
                        slug = product.slug,
                        permalink = product.permalink,
                        dateCreated = product.dateCreated,
                        type = product.type,
                        status = product.status,
                        featured = product.featured,
                        description = product.description,
                        shortDescription = product.shortDescription,
                        sku = product.sku,
                        price = product.price,
                        regularPrice = product.regularPrice,
                        salePrice = product.salePrice,
                        onSale = product.onSale,
                        stockQuantity = product.stockQuantity,
                        stockStatus = product.stockStatus,
                        averageRating = product.averageRating,
                        ratingCount = product.ratingCount,
                        categories = product.categories.map { it.name },
                        images = product.images.map { it.src },
                        variations = product.variations
                    )
                    productCacheDao.insertProduct(cacheEntity)
                    
                    emit(ApiResponse.Success(product))
                } else {
                    emit(ApiResponse.Error("Product not found"))
                }
            } else {
                emit(ApiResponse.Error("Failed to fetch product: ${response.message()}"))
            }
        } catch (e: HttpException) {
            emit(ApiResponse.Error("Network error: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(ApiResponse.Error("Connection error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Unknown error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun searchProducts(query: String): Flow<ApiResponse<List<Product>>> {
        return getProducts(search = query)
    }
    
    override suspend fun getFeaturedProducts(): Flow<ApiResponse<List<Product>>> {
        return getProducts(featured = true)
    }
    
    override suspend fun getProductsByCategory(categoryId: String): Flow<ApiResponse<List<Product>>> {
        return getProducts(category = categoryId)
    }
    
    override suspend fun getProductCategories(): Flow<ApiResponse<List<ProductCategory>>> = flow {
        emit(ApiResponse.Loading())
        
        try {
            val response = apiService.getProductCategories()
            
            if (response.isSuccessful) {
                val categories = response.body() ?: emptyList()
                emit(ApiResponse.Success(categories))
            } else {
                emit(ApiResponse.Error("Failed to fetch categories: ${response.message()}"))
            }
        } catch (e: HttpException) {
            emit(ApiResponse.Error("Network error: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(ApiResponse.Error("Connection error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Unknown error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun refreshProducts() {
        // Clear old cache and fetch fresh data
        val threshold = System.currentTimeMillis() - (24 * 60 * 60 * 1000) // 24 hours
        productCacheDao.deleteOldCache(threshold)
    }
    
    override fun getCachedProducts(): Flow<List<Product>> {
        return productCacheDao.getAllCachedProducts().map { cacheEntities ->
            cacheEntities.map { cache ->
                mapCacheToProduct(cache)
            }
        }
    }
    
    override fun getCachedFeaturedProducts(): Flow<List<Product>> {
        return productCacheDao.getFeaturedProducts().map { cacheEntities ->
            cacheEntities.map { cache ->
                mapCacheToProduct(cache)
            }
        }
    }
    
    private fun mapCacheToProduct(cache: ProductCacheEntity): Product {
        return Product(
            id = cache.id,
            name = cache.name,
            slug = cache.slug,
            permalink = cache.permalink,
            dateCreated = cache.dateCreated,
            dateModified = "",
            type = cache.type,
            status = cache.status,
            featured = cache.featured,
            catalogVisibility = "visible",
            description = cache.description,
            shortDescription = cache.shortDescription,
            sku = cache.sku,
            price = cache.price,
            regularPrice = cache.regularPrice,
            salePrice = cache.salePrice,
            onSale = cache.onSale,
            purchasable = true,
            totalSales = 0,
            virtual = false,
            downloadable = false,
            externalUrl = "",
            buttonText = "",
            taxStatus = "taxable",
            taxClass = "",
            manageStock = false,
            stockQuantity = cache.stockQuantity,
            stockStatus = cache.stockStatus,
            backorders = "no",
            backordersAllowed = false,
            backordered = false,
            soldIndividually = false,
            weight = "",
            dimensions = com.hayuwidyas.data.model.ProductDimensions("", "", ""),
            shippingRequired = true,
            shippingTaxable = true,
            shippingClass = "",
            shippingClassId = 0,
            reviewsAllowed = true,
            averageRating = cache.averageRating,
            ratingCount = cache.ratingCount,
            relatedIds = emptyList(),
            upsellIds = emptyList(),
            crossSellIds = emptyList(),
            parentId = 0,
            purchaseNote = "",
            categories = cache.categories.map { name ->
                com.hayuwidyas.data.model.ProductCategory(0, name, "")
            },
            tags = emptyList(),
            images = cache.images.mapIndexed { index, src ->
                com.hayuwidyas.data.model.ProductImage(
                    id = index,
                    dateCreated = cache.dateCreated,
                    dateModified = cache.dateCreated,
                    src = src,
                    name = "",
                    alt = ""
                )
            },
            attributes = emptyList(),
            defaultAttributes = emptyList(),
            variations = cache.variations,
            groupedProducts = emptyList(),
            menuOrder = 0,
            metaData = emptyList()
        )
    }
}
