package com.hayuwidyas.domain.usecase

import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.domain.model.DomainWishlistItem
import com.hayuwidyas.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for adding items to wishlist
 */
class AddToWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend operator fun invoke(
        productId: Int,
        productName: String,
        productImage: String,
        price: String,
        regularPrice: String,
        salePrice: String,
        onSale: Boolean,
        userId: String?
    ): ApiResponse<Unit> {
        return wishlistRepository.addToWishlist(
            productId = productId,
            productName = productName,
            productImage = productImage,
            price = price,
            regularPrice = regularPrice,
            salePrice = salePrice,
            onSale = onSale,
            userId = userId
        )
    }
}

/**
 * Use case for getting wishlist items
 */
class GetWishlistItemsUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    operator fun invoke(userId: String?): Flow<List<DomainWishlistItem>> {
        return wishlistRepository.getWishlistItems(userId).map { wishlistItems ->
            wishlistItems.map { item ->
                DomainWishlistItem(
                    id = item.id,
                    productId = item.productId,
                    productName = item.productName,
                    productImage = item.productImage,
                    price = item.price,
                    regularPrice = item.regularPrice,
                    salePrice = item.salePrice,
                    onSale = item.onSale,
                    userId = item.userId
                )
            }
        }
    }
}

/**
 * Use case for getting wishlist item count
 */
class GetWishlistItemCountUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    operator fun invoke(userId: String?): Flow<Int> {
        return wishlistRepository.getWishlistItemCount(userId)
    }
}

/**
 * Use case for checking if a product is in wishlist
 */
class IsProductInWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    operator fun invoke(productId: Int, userId: String?): Flow<Boolean> {
        return wishlistRepository.isProductInWishlist(productId, userId)
    }
}

/**
 * Use case for removing items from wishlist
 */
class RemoveFromWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend operator fun invoke(wishlistItemId: Int): ApiResponse<Unit> {
        return wishlistRepository.removeFromWishlist(wishlistItemId)
    }
}

/**
 * Use case for removing items from wishlist by product ID
 */
class RemoveFromWishlistByProductIdUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend operator fun invoke(productId: Int, userId: String?): ApiResponse<Unit> {
        return wishlistRepository.removeFromWishlistByProductId(productId, userId)
    }
}

/**
 * Use case for clearing the entire wishlist
 */
class ClearWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend operator fun invoke(userId: String?): ApiResponse<Unit> {
        return wishlistRepository.clearWishlist(userId)
    }
}

/**
 * Use case for migrating guest wishlist to user wishlist when user logs in
 */
class MigrateGuestWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend operator fun invoke(newUserId: String): ApiResponse<Unit> {
        return wishlistRepository.migrateGuestWishlistToUser(newUserId)
    }
}

/**
 * Use case for toggling wishlist status of a product
 */
class ToggleWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository,
    private val addToWishlistUseCase: AddToWishlistUseCase,
    private val removeFromWishlistByProductIdUseCase: RemoveFromWishlistByProductIdUseCase
) {
    suspend operator fun invoke(
        productId: Int,
        productName: String,
        productImage: String,
        price: String,
        regularPrice: String,
        salePrice: String,
        onSale: Boolean,
        userId: String?,
        isCurrentlyInWishlist: Boolean
    ): ApiResponse<Boolean> {
        return if (isCurrentlyInWishlist) {
            when (val result = removeFromWishlistByProductIdUseCase(productId, userId)) {
                is ApiResponse.Success -> ApiResponse.Success(false)
                is ApiResponse.Error -> result
                is ApiResponse.Loading -> result
            }
        } else {
            when (val result = addToWishlistUseCase(
                productId, productName, productImage, price, regularPrice, salePrice, onSale, userId
            )) {
                is ApiResponse.Success -> ApiResponse.Success(true)
                is ApiResponse.Error -> result
                is ApiResponse.Loading -> result
            }
        }
    }
}