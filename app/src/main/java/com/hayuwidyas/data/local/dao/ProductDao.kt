package com.hayuwidyas.data.local.dao

import androidx.room.*
import com.hayuwidyas.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Products
 * 
 * Handles all database operations for product caching and offline access.
 */
@Dao
interface ProductDao {
    
    /**
     * Get all products as Flow for reactive updates
     */
    @Query("SELECT * FROM products ORDER BY lastUpdated DESC")
    fun getAllProducts(): Flow<List<ProductEntity>>
    
    /**
     * Get products by category
     */
    @Query("SELECT * FROM products WHERE categories LIKE '%' || :category || '%' ORDER BY lastUpdated DESC")
    fun getProductsByCategory(category: String): Flow<List<ProductEntity>>
    
    /**
     * Get featured products
     */
    @Query("SELECT * FROM products WHERE featured = 1 ORDER BY lastUpdated DESC")
    fun getFeaturedProducts(): Flow<List<ProductEntity>>
    
    /**
     * Get products on sale
     */
    @Query("SELECT * FROM products WHERE onSale = 1 ORDER BY lastUpdated DESC")
    fun getProductsOnSale(): Flow<List<ProductEntity>>
    
    /**
     * Search products by name or description
     */
    @Query("""
        SELECT * FROM products 
        WHERE name LIKE '%' || :query || '%' 
        OR description LIKE '%' || :query || '%'
        OR shortDescription LIKE '%' || :query || '%'
        ORDER BY 
            CASE WHEN name LIKE '%' || :query || '%' THEN 1 ELSE 2 END,
            lastUpdated DESC
    """)
    fun searchProducts(query: String): Flow<List<ProductEntity>>
    
    /**
     * Get products with price range filter
     */
    @Query("""
        SELECT * FROM products 
        WHERE price BETWEEN :minPrice AND :maxPrice 
        ORDER BY price ASC
    """)
    fun getProductsByPriceRange(minPrice: Double, maxPrice: Double): Flow<List<ProductEntity>>
    
    /**
     * Get products sorted by price (low to high)
     */
    @Query("SELECT * FROM products ORDER BY price ASC")
    fun getProductsSortedByPriceLowToHigh(): Flow<List<ProductEntity>>
    
    /**
     * Get products sorted by price (high to low)
     */
    @Query("SELECT * FROM products ORDER BY price DESC")
    fun getProductsSortedByPriceHighToLow(): Flow<List<ProductEntity>>
    
    /**
     * Get products sorted by rating
     */
    @Query("SELECT * FROM products ORDER BY averageRating DESC, ratingCount DESC")
    fun getProductsSortedByRating(): Flow<List<ProductEntity>>
    
    /**
     * Get products sorted by popularity (total sales approximated by rating count)
     */
    @Query("SELECT * FROM products ORDER BY ratingCount DESC, averageRating DESC")
    fun getProductsSortedByPopularity(): Flow<List<ProductEntity>>
    
    /**
     * Get single product by ID
     */
    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: Int): ProductEntity?
    
    /**
     * Get single product by ID as Flow
     */
    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductByIdFlow(productId: Int): Flow<ProductEntity?>
    
    /**
     * Get products by IDs
     */
    @Query("SELECT * FROM products WHERE id IN (:productIds)")
    suspend fun getProductsByIds(productIds: List<Int>): List<ProductEntity>
    
    /**
     * Get products by IDs as Flow
     */
    @Query("SELECT * FROM products WHERE id IN (:productIds)")
    fun getProductsByIdsFlow(productIds: List<Int>): Flow<List<ProductEntity>>
    
    /**
     * Insert single product
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)
    
    /**
     * Insert multiple products
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)
    
    /**
     * Update product
     */
    @Update
    suspend fun updateProduct(product: ProductEntity)
    
    /**
     * Delete product by ID
     */
    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: Int)
    
    /**
     * Delete all products
     */
    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()
    
    /**
     * Delete old cached products (older than specified timestamp)
     */
    @Query("DELETE FROM products WHERE lastUpdated < :timestamp")
    suspend fun deleteOldProducts(timestamp: Long)
    
    /**
     * Get product count
     */
    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductCount(): Int
    
    /**
     * Check if product exists
     */
    @Query("SELECT EXISTS(SELECT 1 FROM products WHERE id = :productId)")
    suspend fun productExists(productId: Int): Boolean
    
    /**
     * Get distinct categories
     */
    @Query("SELECT DISTINCT categories FROM products")
    suspend fun getDistinctCategories(): List<String>
    
    /**
     * Get distinct tags
     */
    @Query("SELECT DISTINCT tags FROM products")
    suspend fun getDistinctTags(): List<String>
    
    /**
     * Get products with low stock (for admin purposes)
     */
    @Query("SELECT * FROM products WHERE stockQuantity IS NOT NULL AND stockQuantity <= :threshold")
    suspend fun getProductsWithLowStock(threshold: Int = 5): List<ProductEntity>
    
    /**
     * Get out of stock products
     */
    @Query("SELECT * FROM products WHERE stockStatus = 'outofstock'")
    fun getOutOfStockProducts(): Flow<List<ProductEntity>>
    
    /**
     * Get in stock products
     */
    @Query("SELECT * FROM products WHERE stockStatus = 'instock'")
    fun getInStockProducts(): Flow<List<ProductEntity>>
}
