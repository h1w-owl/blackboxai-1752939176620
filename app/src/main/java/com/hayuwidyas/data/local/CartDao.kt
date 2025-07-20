package com.hayuwidyas.data.local

import androidx.room.*
import com.hayuwidyas.data.model.CartItem
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Cart operations
 */
@Dao
interface CartDao {
    
    @Query("SELECT * FROM cart_items WHERE userId = :userId OR userId IS NULL ORDER BY dateAdded DESC")
    fun getCartItems(userId: String?): Flow<List<CartItem>>
    
    @Query("SELECT * FROM cart_items WHERE id = :id")
    suspend fun getCartItem(id: Int): CartItem?
    
    @Query("SELECT * FROM cart_items WHERE productId = :productId AND (userId = :userId OR userId IS NULL)")
    suspend fun getCartItemByProductId(productId: Int, userId: String?): CartItem?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)
    
    @Update
    suspend fun updateCartItem(cartItem: CartItem)
    
    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)
    
    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun deleteCartItemById(id: Int)
    
    @Query("DELETE FROM cart_items WHERE productId = :productId AND (userId = :userId OR userId IS NULL)")
    suspend fun deleteCartItemByProductId(productId: Int, userId: String?)
    
    @Query("DELETE FROM cart_items WHERE userId = :userId OR userId IS NULL")
    suspend fun clearCart(userId: String?)
    
    @Query("SELECT COUNT(*) FROM cart_items WHERE userId = :userId OR userId IS NULL")
    fun getCartItemCount(userId: String?): Flow<Int>
    
    @Query("SELECT SUM(CAST(price AS REAL) * quantity) FROM cart_items WHERE userId = :userId OR userId IS NULL")
    fun getCartTotal(userId: String?): Flow<Double?>
    
    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :id")
    suspend fun updateCartItemQuantity(id: Int, quantity: Int)
    
    @Query("UPDATE cart_items SET userId = :newUserId WHERE userId IS NULL")
    suspend fun migrateGuestCartToUser(newUserId: String)
}