package com.hayuwidyas.domain.repository

import com.hayuwidyas.domain.model.CartItem
import com.hayuwidyas.domain.model.Product
import com.hayuwidyas.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Cart Repository Interface
 * 
 * Defines the contract for cart-related data operations.
 * Handles local cart storage and synchronization with remote services.
 */
interface CartRepository {
    
    /**
     * Get all cart items
     */
    fun getCartItems(): Flow<List<CartItem>>
    
    /**
     * Get cart items count
     */
    fun getCartItemsCount(): Flow<Int>
    
    /**
     * Get total cart quantity
     */
    fun getTotalCartQuantity(): Flow<Int>
    
    /**
     * Get cart total price
     */
    fun getCartTotalPrice(): Flow<Double>
    
    /**
     * Add product to cart
     */
    suspend fun addToCart(
        product: Product,
        quantity: Int = 1,
        selectedColor: String? = null,
        selectedSize: String? = null
    ): Resource<CartItem>
    
    /**
     * Update cart item quantity
     */
    suspend fun updateCartItemQuantity(
        cartItemId: String,
        quantity: Int
    ): Resource<CartItem>
    
    /**
     * Increment cart item quantity
     */
    suspend fun incrementCartItemQuantity(cartItemId: String): Resource<CartItem>
    
    /**
     * Decrement cart item quantity
     */
    suspend fun decrementCartItemQuantity(cartItemId: String): Resource<CartItem>
    
    /**
     * Remove item from cart
     */
    suspend fun removeFromCart(cartItemId: String): Resource<Unit>
    
    /**
     * Remove product from cart (all variations)
     */
    suspend fun removeProductFromCart(productId: Int): Resource<Unit>
    
    /**
     * Clear entire cart
     */
    suspend fun clearCart(): Resource<Unit>
    
    /**
     * Check if product is in cart
     */
    suspend fun isProductInCart(productId: Int): Boolean
    
    /**
     * Check if specific variation is in cart
     */
    suspend fun isVariationInCart(
        productId: Int,
        selectedColor: String?,
        selectedSize: String?
    ): Boolean
    
    /**
     * Get cart item by product and variations
     */
    suspend fun getCartItemByProductAndVariations(
        productId: Int,
        selectedColor: String?,
        selectedSize: String?
    ): CartItem?
    
    /**
     * Sync cart with Firebase (when user logs in)
     */
    suspend fun syncCartWithFirebase(userId: String): Resource<Unit>
    
    /**
     * Load cart from Firebase
     */
    suspend fun loadCartFromFirebase(userId: String): Resource<List<CartItem>>
    
    /**
     * Save cart to Firebase
     */
    suspend fun saveCartToFirebase(userId: String): Resource<Unit>
    
    /**
     * Merge local cart with Firebase cart
     */
    suspend fun mergeCartWithFirebase(userId: String): Resource<List<CartItem>>
    
    /**
     * Clean up old cart items
     */
    suspend fun cleanupOldCartItems(olderThanDays: Int = 30): Resource<Unit>
    
    /**
     * Get cart summary
     */
    suspend fun getCartSummary(): CartSummary
    
    /**
     * Validate cart items (check stock, prices, etc.)
     */
    suspend fun validateCartItems(): Resource<List<CartValidationResult>>
    
    /**
     * Update product prices in cart
     */
    suspend fun updateProductPricesInCart(): Resource<Unit>
}

/**
 * Cart summary data class
 */
data class CartSummary(
    val itemCount: Int,
    val totalQuantity: Int,
    val subtotal: Double,
    val tax: Double = 0.0,
    val shipping: Double = 0.0,
    val total: Double
) {
    val formattedSubtotal: String
        get() = "Rp ${String.format("%,.0f", subtotal)}"
    
    val formattedTax: String
        get() = "Rp ${String.format("%,.0f", tax)}"
    
    val formattedShipping: String
        get() = if (shipping > 0) "Rp ${String.format("%,.0f", shipping)}" else "Free"
    
    val formattedTotal: String
        get() = "Rp ${String.format("%,.0f", total)}"
}

/**
 * Cart validation result
 */
data class CartValidationResult(
    val cartItemId: String,
    val productId: Int,
    val isValid: Boolean,
    val issues: List<CartValidationIssue>
)

/**
 * Cart validation issues
 */
sealed class CartValidationIssue {
    object OutOfStock : CartValidationIssue()
    object PriceChanged : CartValidationIssue()
    object ProductUnavailable : CartValidationIssue()
    data class InsufficientStock(val availableQuantity: Int) : CartValidationIssue()
    data class VariationUnavailable(val variation: String) : CartValidationIssue()
}
