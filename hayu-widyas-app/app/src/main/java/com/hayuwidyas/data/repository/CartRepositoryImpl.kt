package com.hayuwidyas.data.repository

import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.data.local.CartDao
import com.hayuwidyas.data.model.CartItem
import com.hayuwidyas.data.model.Order
import com.hayuwidyas.data.model.CreateOrderRequest
import com.hayuwidyas.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    
    override fun getCartItems(userId: String?): Flow<List<CartItem>> {
        return cartDao.getCartItems(userId)
    }
    
    override fun getCartItemCount(userId: String?): Flow<Int> {
        return cartDao.getCartItemCount(userId)
    }
    
    override fun getCartTotal(userId: String?): Flow<Double> {
        return cartDao.getCartTotal(userId).map { it ?: 0.0 }
    }
    
    override suspend fun addToCart(
        productId: Int,
        productName: String,
        productImage: String,
        price: String,
        quantity: Int,
        userId: String?
    ): ApiResponse<Unit> {
        return try {
            val existingItem = cartDao.getCartItemByProductId(productId, userId)
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
                cartDao.updateCartItem(updatedItem)
            } else {
                val cartItem = CartItem(
                    productId = productId,
                    productName = productName,
                    productImage = productImage,
                    price = price,
                    quantity = quantity,
                    userId = userId
                )
                cartDao.insertCartItem(cartItem)
            }
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to add to cart: ${e.message}")
        }
    }
    
    override suspend fun updateCartItemQuantity(cartItemId: Int, quantity: Int): ApiResponse<Unit> {
        return try {
            cartDao.updateCartItemQuantity(cartItemId, quantity)
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to update cart item: ${e.message}")
        }
    }
    
    override suspend fun removeFromCart(cartItemId: Int): ApiResponse<Unit> {
        return try {
            cartDao.deleteCartItemById(cartItemId)
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to remove from cart: ${e.message}")
        }
    }
    
    override suspend fun removeFromCartByProductId(productId: Int, userId: String?): ApiResponse<Unit> {
        return try {
            cartDao.deleteCartItemByProductId(productId, userId)
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to remove from cart: ${e.message}")
        }
    }
    
    override suspend fun clearCart(userId: String?): ApiResponse<Unit> {
        return try {
            cartDao.clearCart(userId)
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to clear cart: ${e.message}")
        }
    }
    
    override suspend fun migrateGuestCartToUser(newUserId: String): ApiResponse<Unit> {
        return try {
            cartDao.migrateGuestCartToUser(newUserId)
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to migrate cart: ${e.message}")
        }
    }
    
    override suspend fun createOrder(orderRequest: CreateOrderRequest): Flow<ApiResponse<Order>> = flow {
        emit(ApiResponse.Error("Order creation not implemented yet"))
    }
    
    override suspend fun syncCartWithServer(userId: String): Flow<ApiResponse<Unit>> = flow {
        emit(ApiResponse.Success(Unit))
    }
}
