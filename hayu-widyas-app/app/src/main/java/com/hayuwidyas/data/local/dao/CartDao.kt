package com.hayuwidyas.data.local.dao

import androidx.room.*
import com.hayuwidyas.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Cart Items
 * 
 * Handles all database operations for shopping cart functionality.
 */
@Dao
interface CartDao {
    
    /**
     * Get all cart items as Flow for reactive updates
     */
    @Query("SELECT * FROM cart_items ORDER BY addedAt DESC")
    fun getAllCartItems(): Flow<List<CartItemEntity>>
    
    /**
     * Get cart item by ID
     */
    @Query("SELECT * FROM cart_items WHERE id = :itemId")
    suspend fun getCartItemById(itemId: String): CartItemEntity?
    
    /**
     * Get cart item by product ID and variations
     */
    @Query("""
        SELECT * FROM cart_items 
        WHERE productId = :productId 
        AND selectedColor = :selectedColor 
        AND selectedSize = :selectedSize
    """)
    suspend fun getCartItemByProductAndVariations(
        productId: Int,
        selectedColor: String?,
        selectedSize: String?
    ): CartItemEntity?
    
    /**
     * Get cart items count
     */
    @Query("SELECT COUNT(*) FROM cart_items")
    fun getCartItemsCount(): Flow<Int>
    
    /**
     * Get total cart quantity
     */
    @Query("SELECT SUM(quantity) FROM cart_items")
    fun getTotalCartQuantity(): Flow<Int?>
    
    /**
     * Get cart total price
     */
    @Query("SELECT SUM(productPrice * quantity) FROM cart_items")
    fun getCartTotalPrice(): Flow<Double?>
    
    /**
     * Check if product is in cart
     */
    @Query("SELECT EXISTS(SELECT 1 FROM cart_items WHERE productId = :productId)")
    suspend fun isProductInCart(productId: Int): Boolean
    
    /**
     * Check if specific variation is in cart
     */
    @Query("""
        SELECT EXISTS(SELECT 1 FROM cart_items 
        WHERE productId = :productId 
        AND selectedColor = :selectedColor 
        AND selectedSize = :selectedSize)
    """)
    suspend fun isVariationInCart(
        productId: Int,
        selectedColor: String?,
        selectedSize: String?
    ): Boolean
    
    /**
     * Insert cart item
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)
    
    /**
     * Insert multiple cart items
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItems(cartItems: List<CartItemEntity>)
    
    /**
     * Update cart item
     */
    @Update
    suspend fun updateCartItem(cartItem: CartItemEntity)
    
    /**
     * Update cart item quantity
     */
    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :itemId")
    suspend fun updateCartItemQuantity(itemId: String, quantity: Int)
    
    /**
     * Increment cart item quantity
     */
    @Query("UPDATE cart_items SET quantity = quantity + 1 WHERE id = :itemId")
    suspend fun incrementCartItemQuantity(itemId: String)
    
    /**
     * Decrement cart item quantity
     */
    @Query("UPDATE cart_items SET quantity = quantity - 1 WHERE id = :itemId AND quantity > 1")
    suspend fun decrementCartItemQuantity(itemId: String)
    
    /**
     * Delete cart item by ID
     */
    @Query("DELETE FROM cart_items WHERE id = :itemId")
    suspend fun deleteCartItemById(itemId: String)
    
    /**
     * Delete cart item by product ID
     */
    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteCartItemByProductId(productId: Int)
    
    /**
     * Delete all cart items
     */
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
    
    /**
     * Delete cart items older than specified timestamp
     */
    @Query("DELETE FROM cart_items WHERE addedAt < :timestamp")
    suspend fun deleteOldCartItems(timestamp: Long)
    
    /**
     * Get cart items by product IDs
     */
    @Query("SELECT * FROM cart_items WHERE productId IN (:productIds)")
    suspend fun getCartItemsByProductIds(productIds: List<Int>): List<CartItemEntity>
    
    /**
     * Get cart summary (count and total)
     */
    @Query("""
        SELECT 
            COUNT(*) as itemCount,
            SUM(quantity) as totalQuantity,
            SUM(productPrice * quantity) as totalPrice
        FROM cart_items
    """)
    suspend fun getCartSummary(): CartSummary?
    
    /**
     * Update product price in cart items (when product price changes)
     */
    @Query("UPDATE cart_items SET productPrice = :newPrice WHERE productId = :productId")
    suspend fun updateProductPriceInCart(productId: Int, newPrice: Double)
    
    /**
     * Get cart items added in last N days
     */
    @Query("SELECT * FROM cart_items WHERE addedAt > :timestamp ORDER BY addedAt DESC")
    suspend fun getRecentCartItems(timestamp: Long): List<CartItemEntity>
}

/**
 * Data class for cart summary
 */
data class CartSummary(
    val itemCount: Int,
    val totalQuantity: Int,
    val totalPrice: Double
)
