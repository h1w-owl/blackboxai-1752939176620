package com.hayuwidyas.data.repository

import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.data.local.WishlistDao
import com.hayuwidyas.data.model.WishlistItem
import com.hayuwidyas.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val wishlistDao: WishlistDao
) : WishlistRepository {
    
    override fun getWishlistItems(userId: String?): Flow<List<WishlistItem>> {
        return wishlistDao.getWishlistItems(userId)
    }
    
    override fun getWishlistItemCount(userId: String?): Flow<Int> {
        return wishlistDao.getWishlistItemCount(userId)
    }
    
    override fun isProductInWishlist(productId: Int, userId: String?): Flow<Boolean> {
        return wishlistDao.isProductInWishlist(productId, userId)
    }
    
    override suspend fun addToWishlist(
        productId: Int,
        productName: String,
        productImage: String,
        price: String,
        regularPrice: String,
        salePrice: String,
        onSale: Boolean,
        userId: String?
    ): ApiResponse<Unit> {
        return try {
            val wishlistItem = WishlistItem(
                productId = productId,
                productName = productName,
                productImage = productImage,
                price = price,
                regularPrice = regularPrice,
                salePrice = salePrice,
                onSale = onSale,
                userId = userId
            )
            wishlistDao.insertWishlistItem(wishlistItem)
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to add to wishlist: ${e.message}")
        }
    }
    
    override suspend fun removeFromWishlist(wishlistItemId: Int): ApiResponse<Unit> {
        return try {
            wishlistDao.deleteWishlistItemById(wishlistItemId)
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to remove from wishlist: ${e.message}")
        }
    }
    
    override suspend fun removeFromWishlistByProductId(productId: Int, userId: String?): ApiResponse<Unit> {
        return try {
            wishlistDao.deleteWishlistItemByProductId(productId, userId)
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to remove from wishlist: ${e.message}")
        }
    }
    
    override suspend fun clearWishlist(userId: String?): ApiResponse<Unit> {
        return try {
            wishlistDao.clearWishlist(userId)
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to clear wishlist: ${e.message}")
        }
    }
    
    override suspend fun migrateGuestWishlistToUser(newUserId: String): ApiResponse<Unit> {
        return try {
            wishlistDao.migrateGuestWishlistToUser(newUserId)
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Failed to migrate wishlist: ${e.message}")
        }
    }
    
    override suspend fun syncWishlistWithServer(userId: String): Flow<ApiResponse<Unit>> = flow {
        emit(ApiResponse.Success(Unit))
    }
}