package com.hayuwidyas.domain.repository

import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.data.model.CartItem
import com.hayuwidyas.data.model.Order
import com.hayuwidyas.data.model.CreateOrderRequest
import kotlinx.coroutines.flow.Flow

/**
 * Cart repository interface for domain layer
 */
interface CartRepository {
    
    fun getCartItems(userId: String?): Flow<List<CartItem>>
    
    fun getCartItemCount(userId: String?): Flow<Int>
    
    fun getCartTotal(userId: String?): Flow<Double>
    
    suspend fun addToCart(
        productId: Int,
        productName: String,
        productImage: String,
        price: String,
        quantity: Int,
        userId: String?
    ): ApiResponse<Unit>
    
    suspend fun updateCartItemQuantity(cartItemId: Int, quantity: Int): ApiResponse<Unit>
    
    suspend fun removeFromCart(cartItemId: Int): ApiResponse<Unit>
    
    suspend fun removeFromCartByProductId(productId: Int, userId: String?): ApiResponse<Unit>
    
    suspend fun clearCart(userId: String?): ApiResponse<Unit>
    
    suspend fun migrateGuestCartToUser(newUserId: String): ApiResponse<Unit>
    
    suspend fun createOrder(orderRequest: CreateOrderRequest): Flow<ApiResponse<Order>>
    
    suspend fun syncCartWithServer(userId: String): Flow<ApiResponse<Unit>>
}
