package com.hayuwidyas.domain.model

/**
 * Domain models for Clean Architecture
 * These models are used in the domain layer and map from data models
 */

data class DomainProduct(
    val id: Int,
    val name: String,
    val slug: String,
    val permalink: String,
    val description: String,
    val shortDescription: String,
    val price: String,
    val regularPrice: String,
    val salePrice: String,
    val onSale: Boolean,
    val stockQuantity: Int?,
    val stockStatus: String,
    val averageRating: String,
    val ratingCount: Int,
    val categories: List<String>,
    val images: List<String>,
    val featured: Boolean,
    val variations: List<Int> = emptyList()
) {
    val formattedPrice: String
        get() = "Rp ${String.format("%,.0f", price.toDoubleOrNull() ?: 0.0)}"
    
    val formattedRegularPrice: String
        get() = "Rp ${String.format("%,.0f", regularPrice.toDoubleOrNull() ?: 0.0)}"
    
    val isInStock: Boolean
        get() = stockStatus == "instock"
    
    val primaryImage: String
        get() = images.firstOrNull() ?: ""
    
    val categoryNames: String
        get() = categories.joinToString(", ")
}

data class DomainCartItem(
    val id: Int,
    val productId: Int,
    val productName: String,
    val productImage: String,
    val price: String,
    val quantity: Int,
    val userId: String?
) {
    val formattedPrice: String
        get() = "Rp ${String.format("%,.0f", price.toDoubleOrNull() ?: 0.0)}"
    
    val totalPrice: Double
        get() = (price.toDoubleOrNull() ?: 0.0) * quantity
    
    val formattedTotalPrice: String
        get() = "Rp ${String.format("%,.0f", totalPrice)}"
}

data class DomainWishlistItem(
    val id: Int,
    val productId: Int,
    val productName: String,
    val productImage: String,
    val price: String,
    val regularPrice: String,
    val salePrice: String,
    val onSale: Boolean,
    val userId: String?
) {
    val formattedPrice: String
        get() = "Rp ${String.format("%,.0f", price.toDoubleOrNull() ?: 0.0)}"
    
    val formattedRegularPrice: String
        get() = "Rp ${String.format("%,.0f", regularPrice.toDoubleOrNull() ?: 0.0)}"
    
    val discountPercentage: Int
        get() {
            val regular = regularPrice.toDoubleOrNull() ?: 0.0
            val sale = salePrice.toDoubleOrNull() ?: regular
            return if (regular > 0 && sale < regular) {
                ((regular - sale) / regular * 100).toInt()
            } else 0
        }
}

data class DomainUser(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val displayName: String,
    val photoUrl: String?,
    val isEmailVerified: Boolean
) {
    val fullName: String
        get() = "$firstName $lastName".trim()
    
    val initials: String
        get() = "${firstName.firstOrNull() ?: ""}${lastName.firstOrNull() ?: ""}".uppercase()
}

data class DomainOrder(
    val id: Int,
    val status: String,
    val total: String,
    val dateCreated: String,
    val lineItems: List<DomainOrderLineItem>
) {
    val formattedTotal: String
        get() = "Rp ${String.format("%,.0f", total.toDoubleOrNull() ?: 0.0)}"
    
    val itemCount: Int
        get() = lineItems.sumOf { it.quantity }
}

data class DomainOrderLineItem(
    val id: Int,
    val name: String,
    val productId: Int,
    val quantity: Int,
    val total: String,
    val image: String?
) {
    val formattedTotal: String
        get() = "Rp ${String.format("%,.0f", total.toDoubleOrNull() ?: 0.0)}"
}

data class DomainCategory(
    val id: Int,
    val name: String,
    val slug: String,
    val description: String?,
    val image: String?,
    val count: Int
)

// Filter and sort options
data class ProductFilter(
    val category: String? = null,
    val minPrice: String? = null,
    val maxPrice: String? = null,
    val search: String? = null,
    val featured: Boolean? = null,
    val inStock: Boolean? = null
)

enum class ProductSortOption(val orderBy: String, val order: String, val displayName: String) {
    NEWEST("date", "desc", "Newest"),
    OLDEST("date", "asc", "Oldest"),
    PRICE_LOW_TO_HIGH("price", "asc", "Price: Low to High"),
    PRICE_HIGH_TO_LOW("price", "desc", "Price: High to Low"),
    NAME_A_TO_Z("title", "asc", "Name: A-Z"),
    NAME_Z_TO_A("title", "desc", "Name: Z-A"),
    RATING("rating", "desc", "Rating"),
    POPULARITY("popularity", "desc", "Popularity")
}

// UI States
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : UiState<Nothing>()
    object Empty : UiState<Nothing>()
}

// Pagination
data class PaginatedData<T>(
    val data: List<T>,
    val currentPage: Int,
    val totalPages: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean
) {
    val isEmpty: Boolean get() = data.isEmpty()
    val isNotEmpty: Boolean get() = data.isNotEmpty()
}