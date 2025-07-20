package com.hayuwidyas.domain.repository

import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.data.model.WishlistItem
import kotlinx.coroutines.flow.Flow

/**
 * Wishlist repository interface for domain layer
 */
interface WishlistRepository {
    
    fun getWishlistItems(userId: String?): Flow<List<WishlistItem>>
    
    fun getWishlistItemCount(userId: String?): Flow<Int>
    
    fun isProductInWishlist(productId: Int, userId: String?): Flow<Boolean>
    
    suspend fun addToWishlist(
        productId: Int,
        productName: String,
        productImage: String,
        price: String,
        regularPrice: String,
        salePrice: String,
        onSale: Boolean,
        userId: String?
    ): ApiResponse<Unit>
    
    suspend fun removeFromWishlist(wishlistItemId: Int): ApiResponse<Unit>
    
    suspend fun removeFromWishlistByProductId(productId: Int, userId: String?): ApiResponse<Unit>
    
    suspend fun clearWishlist(userId: String?): ApiResponse<Unit>
    
    suspend fun migrateGuestWishlistToUser(newUserId: String): ApiResponse<Unit>
    
    suspend fun syncWishlistWithServer(userId: String): Flow<ApiResponse<Unit>>
}
