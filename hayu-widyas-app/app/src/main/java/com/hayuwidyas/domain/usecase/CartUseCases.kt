package com.hayuwidyas.domain.usecase

import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.domain.model.DomainCartItem
import com.hayuwidyas.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for adding items to cart
 */
class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(
        productId: Int,
        productName: String,
        productImage: String,
        price: String,
        quantity: Int,
        userId: String?
    ): ApiResponse<Unit> {
        return cartRepository.addToCart(
            productId = productId,
            productName = productName,
            productImage = productImage,
            price = price,
            quantity = quantity,
            userId = userId
        )
    }
}

/**
 * Use case for getting cart items
 */
class GetCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(userId: String?): Flow<List<DomainCartItem>> {
        return cartRepository.getCartItems(userId).map { cartItems ->
            cartItems.map { item ->
                DomainCartItem(
                    id = item.id,
                    productId = item.productId,
                    productName = item.productName,
                    productImage = item.productImage,
                    price = item.price,
                    quantity = item.quantity,
                    userId = item.userId
                )
            }
        }
    }
}

/**
 * Use case for getting cart item count
 */
class GetCartItemCountUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(userId: String?): Flow<Int> {
        return cartRepository.getCartItemCount(userId)
    }
}

/**
 * Use case for getting cart total
 */
class GetCartTotalUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(userId: String?): Flow<Double> {
        return cartRepository.getCartTotal(userId)
    }
}

/**
 * Use case for updating cart item quantity
 */
class UpdateCartItemQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItemId: Int, quantity: Int): ApiResponse<Unit> {
        return cartRepository.updateCartItemQuantity(cartItemId, quantity)
    }
}

/**
 * Use case for removing items from cart
 */
class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItemId: Int): ApiResponse<Unit> {
        return cartRepository.removeFromCart(cartItemId)
    }
}

/**
 * Use case for clearing the entire cart
 */
class ClearCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(userId: String?): ApiResponse<Unit> {
        return cartRepository.clearCart(userId)
    }
}

/**
 * Use case for migrating guest cart to user cart when user logs in
 */
class MigrateGuestCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(newUserId: String): ApiResponse<Unit> {
        return cartRepository.migrateGuestCartToUser(newUserId)
    }
}