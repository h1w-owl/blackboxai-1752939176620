package com.hayuwidyas.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Product Cache operations
 */
@Dao
interface ProductCacheDao {
    
    @Query("SELECT * FROM product_cache ORDER BY cachedAt DESC")
    fun getAllCachedProducts(): Flow<List<ProductCacheEntity>>
    
    @Query("SELECT * FROM product_cache WHERE id = :id")
    suspend fun getCachedProduct(id: Int): ProductCacheEntity?
    
    @Query("SELECT * FROM product_cache WHERE featured = 1 ORDER BY cachedAt DESC")
    fun getFeaturedProducts(): Flow<List<ProductCacheEntity>>
    
    @Query("SELECT * FROM product_cache WHERE onSale = 1 ORDER BY cachedAt DESC")
    fun getOnSaleProducts(): Flow<List<ProductCacheEntity>>
    
    @Query("SELECT * FROM product_cache WHERE name LIKE '%' || :searchQuery || '%' OR description LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    fun searchProducts(searchQuery: String): Flow<List<ProductCacheEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductCacheEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductCacheEntity>)
    
    @Update
    suspend fun updateProduct(product: ProductCacheEntity)
    
    @Delete
    suspend fun deleteProduct(product: ProductCacheEntity)
    
    @Query("DELETE FROM product_cache WHERE id = :id")
    suspend fun deleteProductById(id: Int)
    
    @Query("DELETE FROM product_cache")
    suspend fun clearAllProducts()
    
    @Query("DELETE FROM product_cache WHERE cachedAt < :threshold")
    suspend fun deleteOldCache(threshold: Long)
    
    @Query("SELECT COUNT(*) FROM product_cache")
    suspend fun getCacheCount(): Int
    
    @Query("SELECT * FROM product_cache WHERE cachedAt > :since ORDER BY cachedAt DESC LIMIT :limit")
    suspend fun getRecentlyViewed(since: Long, limit: Int = 10): List<ProductCacheEntity>
}

/**
 * Data Access Object for Category Cache operations
 */
@Dao
interface CategoryCacheDao {
    
    @Query("SELECT * FROM category_cache ORDER BY name ASC")
    fun getAllCachedCategories(): Flow<List<CategoryCacheEntity>>
    
    @Query("SELECT * FROM category_cache WHERE id = :id")
    suspend fun getCachedCategory(id: Int): CategoryCacheEntity?
    
    @Query("SELECT * FROM category_cache WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    fun searchCategories(searchQuery: String): Flow<List<CategoryCacheEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryCacheEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryCacheEntity>)
    
    @Update
    suspend fun updateCategory(category: CategoryCacheEntity)
    
    @Delete
    suspend fun deleteCategory(category: CategoryCacheEntity)
    
    @Query("DELETE FROM category_cache WHERE id = :id")
    suspend fun deleteCategoryById(id: Int)
    
    @Query("DELETE FROM category_cache")
    suspend fun clearAllCategories()
    
    @Query("DELETE FROM category_cache WHERE cachedAt < :threshold")
    suspend fun deleteOldCache(threshold: Long)
    
    @Query("SELECT COUNT(*) FROM category_cache")
    suspend fun getCacheCount(): Int
}