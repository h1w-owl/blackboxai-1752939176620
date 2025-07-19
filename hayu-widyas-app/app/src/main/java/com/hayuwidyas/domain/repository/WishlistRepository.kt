package com.hayuwidyas.domain.repository

import com.hayuwidyas.domain.model.Product
import com.hayuwidyas.domain.model.WishlistItem
import com.hayuwidyas.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Wishlist Repository Interface
 * 
 * Defines the contract for wishlist-related data operations.
 * Handles local wishlist storage and synchronization with Firebase.
 */
interface WishlistRepository {
    
    /**
     * Get all wishlist items
     */
    fun getWishlistItems(): Flow<List<WishlistItem>>
    
    /**
     * Get wishlist items count
     */
    fun getWishlistItemsCount(): Flow<Int>
    
    /**
     * Get wishlist product IDs
     */
    fun getWishlistProductIds(): Flow<List<Int>>
    
    /**
     * Add product to wishlist
     */
    suspend fun addToWishlist(product: Product): Resource<WishlistItem>
    
    /**
     * Remove product from wishlist
     */
    suspend fun removeFromWishlist(productId: Int): Resource<Unit>
    
    /**
     * Toggle product in wishlist
     */
    suspend fun toggleWishlist(product: Product): Resource<Boolean>
    
    /**
     * Check if product is in wishlist
     */
    suspend fun isProductInWishlist(productId: Int): Boolean
    
    /**
     * Check if product is in wishlist as Flow
     */
    fun isProductInWishlistFlow(productId: Int): Flow<Boolean>
    
    /**
     * Clear entire wishlist
     */
    suspend fun clearWishlist(): Resource<Unit>
    
    /**
     * Get wishlist items sorted by price (low to high)
     */
    fun getWishlistItemsSortedByPriceLowToHigh(): Flow<List<WishlistItem>>
    
    /**
     * Get wishlist items sorted by price (high to low)
     */
    fun getWishlistItemsSortedByPriceHighToLow(): Flow<List<WishlistItem>>
    
    /**
     * Get wishlist items sorted by date added (newest first)
     */
    fun getWishlistItemsSortedByDateNewest(): Flow<List<WishlistItem>>
    
    /**
     * Get wishlist items sorted by date added (oldest first)
     */
    fun getWishlistItemsSortedByDateOldest(): Flow<List<WishlistItem>>
    
    /**
     * Get wishlist items sorted by name
     */
    fun getWishlistItemsSortedByName(): Flow<List<WishlistItem>>
    
    /**
     * Search wishlist items
     */
    suspend fun searchWishlistItems(query: String): List<WishlistItem>
    
    /**
     * Get wishlist items by price range
     */
    suspend fun getWishlistItemsByPriceRange(
        minPrice: Double,
        maxPrice: Double
    ): List<WishlistItem>
    
    /**
     * Get recent wishlist items (added in last N days)
     */
    suspend fun getRecentWishlistItems(days: Int = 7): List<WishlistItem>
    
    /**
     * Sync wishlist with Firebase (when user logs in)
     */
    suspend fun syncWishlistWithFirebase(userId: String): Resource<Unit>
    
    /**
     * Load wishlist from Firebase
     */
    suspend fun loadWishlistFromFirebase(userId: String): Resource<List<WishlistItem>>
    
    /**
     * Save wishlist to Firebase
     */
    suspend fun saveWishlistToFirebase(userId: String): Resource<Unit>
    
    /**
     * Merge local wishlist with Firebase wishlist
     */
    suspend fun mergeWishlistWithFirebase(userId: String): Resource<List<WishlistItem>>
    
    /**
     * Clean up old wishlist items
     */
    suspend fun cleanupOldWishlistItems(olderThanDays: Int = 90): Resource<Unit>
    
    /**
     * Get wishlist statistics
     */
    suspend fun getWishlistStatistics(): WishlistStatistics
    
    /**
     * Get wishlist items grouped by price range
     */
    suspend fun getWishlistItemsByPriceRangeGrouped(): List<WishlistPriceRangeGroup>
    
    /**
     * Update product prices in wishlist
     */
    suspend fun updateProductPricesInWishlist(): Resource<Unit>
    
    /**
     * Share wishlist
     */
    suspend fun shareWishlist(): Resource<String>
    
    /**
     * Export wishlist to different formats
     */
    suspend fun exportWishlist(format: ExportFormat): Resource<String>
    
    /**
     * Get wishlist recommendations based on current items
     */
    suspend fun getWishlistRecommendations(limit: Int = 5): Resource<List<Product>>
}

/**
 * Wishlist statistics data class
 */
data class WishlistStatistics(
    val totalItems: Int,
    val averagePrice: Double,
    val minPrice: Double,
    val maxPrice: Double,
    val totalValue: Double,
    val mostExpensiveItem: String,
    val cheapestItem: String,
    val addedThisWeek: Int,
    val addedThisMonth: Int
) {
    val formattedAveragePrice: String
        get() = "Rp ${String.format("%,.0f", averagePrice)}"
    
    val formattedMinPrice: String
        get() = "Rp ${String.format("%,.0f", minPrice)}"
    
    val formattedMaxPrice: String
        get() = "Rp ${String.format("%,.0f", maxPrice)}"
    
    val formattedTotalValue: String
        get() = "Rp ${String.format("%,.0f", totalValue)}"
}

/**
 * Wishlist price range grouping
 */
data class WishlistPriceRangeGroup(
    val priceRange: String,
    val count: Int,
    val percentage: Double
)

/**
 * Export formats for wishlist
 */
enum class ExportFormat {
    JSON,
    CSV,
    PDF,
    TEXT
}

/**
 * Wishlist sort options
 */
enum class WishlistSortOption {
    DATE_ADDED_NEWEST,
    DATE_ADDED_OLDEST,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    NAME_A_TO_Z,
    NAME_Z_TO_A
}

/**
 * Wishlist filter options
 */
data class WishlistFilter(
    val priceRange: Pair<Double, Double>? = null,
    val categories: List<String>? = null,
    val tags: List<String>? = null,
    val dateRange: Pair<Long, Long>? = null,
    val inStock: Boolean? = null,
    val onSale: Boolean? = null
)
