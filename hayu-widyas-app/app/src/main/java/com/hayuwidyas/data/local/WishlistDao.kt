package com.hayuwidyas.data.local

import androidx.room.*
import com.hayuwidyas.data.model.WishlistItem
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Wishlist operations
 */
@Dao
interface WishlistDao {
    
    @Query("SELECT * FROM wishlist_items WHERE userId = :userId OR userId IS NULL ORDER BY dateAdded DESC")
    fun getWishlistItems(userId: String?): Flow<List<WishlistItem>>
    
    @Query("SELECT * FROM wishlist_items WHERE id = :id")
    suspend fun getWishlistItem(id: Int): WishlistItem?
    
    @Query("SELECT * FROM wishlist_items WHERE productId = :productId AND (userId = :userId OR userId IS NULL)")
    suspend fun getWishlistItemByProductId(productId: Int, userId: String?): WishlistItem?
    
    @Query("SELECT EXISTS(SELECT 1 FROM wishlist_items WHERE productId = :productId AND (userId = :userId OR userId IS NULL))")
    fun isProductInWishlist(productId: Int, userId: String?): Flow<Boolean>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlistItem(wishlistItem: WishlistItem)
    
    @Update
    suspend fun updateWishlistItem(wishlistItem: WishlistItem)
    
    @Delete
    suspend fun deleteWishlistItem(wishlistItem: WishlistItem)
    
    @Query("DELETE FROM wishlist_items WHERE id = :id")
    suspend fun deleteWishlistItemById(id: Int)
    
    @Query("DELETE FROM wishlist_items WHERE productId = :productId AND (userId = :userId OR userId IS NULL)")
    suspend fun deleteWishlistItemByProductId(productId: Int, userId: String?)
    
    @Query("DELETE FROM wishlist_items WHERE userId = :userId OR userId IS NULL")
    suspend fun clearWishlist(userId: String?)
    
    @Query("SELECT COUNT(*) FROM wishlist_items WHERE userId = :userId OR userId IS NULL")
    fun getWishlistItemCount(userId: String?): Flow<Int>
    
    @Query("UPDATE wishlist_items SET userId = :newUserId WHERE userId IS NULL")
    suspend fun migrateGuestWishlistToUser(newUserId: String)
}