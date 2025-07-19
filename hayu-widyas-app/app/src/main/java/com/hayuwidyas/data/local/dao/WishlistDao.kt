package com.hayuwidyas.data.local.dao

import androidx.room.*
import com.hayuwidyas.data.local.entity.WishlistItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Wishlist Items
 * 
 * Handles all database operations for wishlist functionality.
 */
@Dao
interface WishlistDao {
    
    /**
     * Get all wishlist items as Flow for reactive updates
     */
    @Query("SELECT * FROM wishlist_items ORDER BY addedAt DESC")
    fun getAllWishlistItems(): Flow<List<WishlistItemEntity>>
    
    /**
     * Get wishlist item by ID
     */
    @Query("SELECT * FROM wishlist_items WHERE id = :itemId")
    suspend fun getWishlistItemById(itemId: String): WishlistItemEntity?
    
    /**
     * Get wishlist item by product ID
     */
    @Query("SELECT * FROM wishlist_items WHERE productId = :productId")
    suspend fun getWishlistItemByProductId(productId: Int): WishlistItemEntity?
    
    /**
     * Get wishlist items count
     */
    @Query("SELECT COUNT(*) FROM wishlist_items")
    fun getWishlistItemsCount(): Flow<Int>
    
    /**
     * Check if product is in wishlist
     */
    @Query("SELECT EXISTS(SELECT 1 FROM wishlist_items WHERE productId = :productId)")
    suspend fun isProductInWishlist(productId: Int): Boolean
    
    /**
     * Check if product is in wishlist as Flow
     */
    @Query("SELECT EXISTS(SELECT 1 FROM wishlist_items WHERE productId = :productId)")
    fun isProductInWishlistFlow(productId: Int): Flow<Boolean>
    
    /**
     * Get wishlist product IDs
     */
    @Query("SELECT productId FROM wishlist_items ORDER BY addedAt DESC")
    suspend fun getWishlistProductIds(): List<Int>
    
    /**
     * Get wishlist product IDs as Flow
     */
    @Query("SELECT productId FROM wishlist_items ORDER BY addedAt DESC")
    fun getWishlistProductIdsFlow(): Flow<List<Int>>
    
    /**
     * Insert wishlist item
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlistItem(wishlistItem: WishlistItemEntity)
    
    /**
     * Insert multiple wishlist items
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlistItems(wishlistItems: List<WishlistItemEntity>)
    
    /**
     * Update wishlist item
     */
    @Update
    suspend fun updateWishlistItem(wishlistItem: WishlistItemEntity)
    
    /**
     * Delete wishlist item by ID
     */
    @Query("DELETE FROM wishlist_items WHERE id = :itemId")
    suspend fun deleteWishlistItemById(itemId: String)
    
    /**
     * Delete wishlist item by product ID
     */
    @Query("DELETE FROM wishlist_items WHERE productId = :productId")
    suspend fun deleteWishlistItemByProductId(productId: Int)
    
    /**
     * Delete all wishlist items
     */
    @Query("DELETE FROM wishlist_items")
    suspend fun clearWishlist()
    
    /**
     * Delete wishlist items older than specified timestamp
     */
    @Query("DELETE FROM wishlist_items WHERE addedAt < :timestamp")
    suspend fun deleteOldWishlistItems(timestamp: Long)
    
    /**
     * Get wishlist items by product IDs
     */
    @Query("SELECT * FROM wishlist_items WHERE productId IN (:productIds)")
    suspend fun getWishlistItemsByProductIds(productIds: List<Int>): List<WishlistItemEntity>
    
    /**
     * Get wishlist items added in last N days
     */
    @Query("SELECT * FROM wishlist_items WHERE addedAt > :timestamp ORDER BY addedAt DESC")
    suspend fun getRecentWishlistItems(timestamp: Long): List<WishlistItemEntity>
    
    /**
     * Get wishlist items with price range filter
     */
    @Query("""
        SELECT * FROM wishlist_items 
        WHERE productPrice BETWEEN :minPrice AND :maxPrice 
        ORDER BY addedAt DESC
    """)
    suspend fun getWishlistItemsByPriceRange(minPrice: Double, maxPrice: Double): List<WishlistItemEntity>
    
    /**
     * Search wishlist items by product name
     */
    @Query("""
        SELECT * FROM wishlist_items 
        WHERE productName LIKE '%' || :query || '%' 
        ORDER BY addedAt DESC
    """)
    suspend fun searchWishlistItems(query: String): List<WishlistItemEntity>
    
    /**
     * Get wishlist items sorted by price (low to high)
     */
    @Query("SELECT * FROM wishlist_items ORDER BY productPrice ASC")
    fun getWishlistItemsSortedByPriceLowToHigh(): Flow<List<WishlistItemEntity>>
    
    /**
     * Get wishlist items sorted by price (high to low)
     */
    @Query("SELECT * FROM wishlist_items ORDER BY productPrice DESC")
    fun getWishlistItemsSortedByPriceHighToLow(): Flow<List<WishlistItemEntity>>
    
    /**
     * Get wishlist items sorted by date added (newest first)
     */
    @Query("SELECT * FROM wishlist_items ORDER BY addedAt DESC")
    fun getWishlistItemsSortedByDateNewest(): Flow<List<WishlistItemEntity>>
    
    /**
     * Get wishlist items sorted by date added (oldest first)
     */
    @Query("SELECT * FROM wishlist_items ORDER BY addedAt ASC")
    fun getWishlistItemsSortedByDateOldest(): Flow<List<WishlistItemEntity>>
    
    /**
     * Get wishlist items sorted by product name
     */
    @Query("SELECT * FROM wishlist_items ORDER BY productName ASC")
    fun getWishlistItemsSortedByName(): Flow<List<WishlistItemEntity>>
    
    /**
     * Update product price in wishlist items (when product price changes)
     */
    @Query("UPDATE wishlist_items SET productPrice = :newPrice WHERE productId = :productId")
    suspend fun updateProductPriceInWishlist(productId: Int, newPrice: Double)
    
    /**
     * Update product name in wishlist items (when product name changes)
     */
    @Query("UPDATE wishlist_items SET productName = :newName WHERE productId = :productId")
    suspend fun updateProductNameInWishlist(productId: Int, newName: String)
    
    /**
     * Update product image in wishlist items (when product image changes)
     */
    @Query("UPDATE wishlist_items SET productImage = :newImage WHERE productId = :productId")
    suspend fun updateProductImageInWishlist(productId: Int, newImage: String)
    
    /**
     * Get wishlist statistics
     */
    @Query("""
        SELECT 
            COUNT(*) as totalItems,
            AVG(productPrice) as averagePrice,
            MIN(productPrice) as minPrice,
            MAX(productPrice) as maxPrice,
            SUM(productPrice) as totalValue
        FROM wishlist_items
    """)
    suspend fun getWishlistStatistics(): WishlistStatistics?
    
    /**
     * Get wishlist items grouped by price range
     */
    @Query("""
        SELECT 
            CASE 
                WHEN productPrice < 10000000 THEN 'Under 10M'
                WHEN productPrice < 20000000 THEN '10M - 20M'
                WHEN productPrice < 30000000 THEN '20M - 30M'
                ELSE 'Above 30M'
            END as priceRange,
            COUNT(*) as count
        FROM wishlist_items
        GROUP BY priceRange
        ORDER BY MIN(productPrice)
    """)
    suspend fun getWishlistItemsByPriceRangeGrouped(): List<WishlistPriceRangeGroup>
}

/**
 * Data class for wishlist statistics
 */
data class WishlistStatistics(
    val totalItems: Int,
    val averagePrice: Double,
    val minPrice: Double,
    val maxPrice: Double,
    val totalValue: Double
)

/**
 * Data class for wishlist price range grouping
 */
data class WishlistPriceRangeGroup(
    val priceRange: String,
    val count: Int
)
