package com.hayuwidyas.domain.repository

import com.hayuwidyas.domain.model.Product
import com.hayuwidyas.domain.model.Category
import com.hayuwidyas.domain.model.Review
import com.hayuwidyas.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Product Repository Interface
 * 
 * Defines the contract for product-related data operations.
 * Implemented by ProductRepositoryImpl in the data layer.
 */
interface ProductRepository {
    
    // ==================== PRODUCTS ====================
    
    /**
     * Get all products with optional filtering and sorting
     */
    suspend fun getProducts(
        page: Int = 1,
        perPage: Int = 20,
        search: String? = null,
        category: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        sortBy: String = "date",
        sortOrder: String = "desc",
        featured: Boolean? = null,
        onSale: Boolean? = null,
        forceRefresh: Boolean = false
    ): Flow<Resource<List<Product>>>
    
    /**
     * Get single product by ID
     */
    suspend fun getProduct(
        productId: Int,
        forceRefresh: Boolean = false
    ): Flow<Resource<Product>>
    
    /**
     * Get featured products
     */
    suspend fun getFeaturedProducts(
        forceRefresh: Boolean = false
    ): Flow<Resource<List<Product>>>
    
    /**
     * Get products on sale
     */
    suspend fun getProductsOnSale(
        forceRefresh: Boolean = false
    ): Flow<Resource<List<Product>>>
    
    /**
     * Search products
     */
    suspend fun searchProducts(
        query: String,
        page: Int = 1,
        perPage: Int = 20
    ): Flow<Resource<List<Product>>>
    
    /**
     * Get products by category
     */
    suspend fun getProductsByCategory(
        category: String,
        page: Int = 1,
        perPage: Int = 20,
        forceRefresh: Boolean = false
    ): Flow<Resource<List<Product>>>
    
    /**
     * Get products by IDs
     */
    suspend fun getProductsByIds(
        productIds: List<Int>,
        forceRefresh: Boolean = false
    ): Flow<Resource<List<Product>>>
    
    /**
     * Get related products
     */
    suspend fun getRelatedProducts(
        productId: Int,
        limit: Int = 4
    ): Flow<Resource<List<Product>>>
    
    // ==================== CATEGORIES ====================
    
    /**
     * Get all categories
     */
    suspend fun getCategories(
        forceRefresh: Boolean = false
    ): Flow<Resource<List<Category>>>
    
    /**
     * Get category by ID
     */
    suspend fun getCategory(
        categoryId: Int,
        forceRefresh: Boolean = false
    ): Flow<Resource<Category>>
    
    // ==================== REVIEWS ====================
    
    /**
     * Get product reviews
     */
    suspend fun getProductReviews(
        productId: Int,
        page: Int = 1,
        perPage: Int = 10
    ): Flow<Resource<List<Review>>>
    
    // ==================== CACHE MANAGEMENT ====================
    
    /**
     * Clear product cache
     */
    suspend fun clearProductCache()
    
    /**
     * Refresh product cache
     */
    suspend fun refreshProductCache()
    
    /**
     * Check if product exists in cache
     */
    suspend fun isProductCached(productId: Int): Boolean
    
    /**
     * Get cached products count
     */
    suspend fun getCachedProductsCount(): Int
}
