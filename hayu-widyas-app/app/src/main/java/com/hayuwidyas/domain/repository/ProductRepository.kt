package com.hayuwidyas.domain.repository

import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.data.model.Product
import com.hayuwidyas.data.model.ProductCategory
import kotlinx.coroutines.flow.Flow

/**
 * Product repository interface for domain layer
 */
interface ProductRepository {
    
    suspend fun getProducts(
        page: Int = 1,
        perPage: Int = 20,
        orderBy: String = "date",
        order: String = "desc",
        category: String? = null,
        minPrice: String? = null,
        maxPrice: String? = null,
        search: String? = null,
        featured: Boolean? = null
    ): Flow<ApiResponse<List<Product>>>
    
    suspend fun getProduct(productId: Int): Flow<ApiResponse<Product>>
    
    suspend fun searchProducts(query: String): Flow<ApiResponse<List<Product>>>
    
    suspend fun getFeaturedProducts(): Flow<ApiResponse<List<Product>>>
    
    suspend fun getProductsByCategory(categoryId: String): Flow<ApiResponse<List<Product>>>
    
    suspend fun getProductCategories(): Flow<ApiResponse<List<ProductCategory>>>
    
    suspend fun refreshProducts()
    
    fun getCachedProducts(): Flow<List<Product>>
    
    fun getCachedFeaturedProducts(): Flow<List<Product>>
}
