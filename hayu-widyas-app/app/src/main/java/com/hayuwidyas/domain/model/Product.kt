package com.hayuwidyas.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Domain model for Product
 * 
 * Clean domain model that represents a product in the business logic layer.
 * Independent of external API structure and optimized for app usage.
 */
@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val shortDescription: String,
    val price: Double,
    val regularPrice: Double,
    val salePrice: Double? = null,
    val onSale: Boolean = false,
    val stockStatus: String,
    val stockQuantity: Int? = null,
    val images: List<String>,
    val categories: List<String>,
    val tags: List<String>,
    val averageRating: Double,
    val ratingCount: Int,
    val featured: Boolean,
    val sku: String,
    val weight: String? = null,
    val dimensions: String? = null,
    val attributes: Map<String, List<String>> = emptyMap()
) : Parcelable {
    
    /**
     * Get the primary image URL
     */
    val primaryImage: String
        get() = images.firstOrNull() ?: ""
    
    /**
     * Check if product is in stock
     */
    val isInStock: Boolean
        get() = stockStatus == "instock" && (stockQuantity == null || stockQuantity > 0)
    
    /**
     * Get formatted price string
     */
    val formattedPrice: String
        get() = formatPrice(if (onSale && salePrice != null) salePrice else price)
    
    /**
     * Get formatted regular price (for sale items)
     */
    val formattedRegularPrice: String
        get() = formatPrice(regularPrice)
    
    /**
     * Check if product has discount
     */
    val hasDiscount: Boolean
        get() = onSale && salePrice != null && salePrice < regularPrice
    
    /**
     * Get discount percentage
     */
    val discountPercentage: Int
        get() = if (hasDiscount && salePrice != null) {
            ((regularPrice - salePrice) / regularPrice * 100).toInt()
        } else 0
    
    /**
     * Get primary category
     */
    val primaryCategory: String
        get() = categories.firstOrNull() ?: ""
    
    /**
     * Check if product is luxury (based on price)
     */
    val isLuxury: Boolean
        get() = price >= 10000000 // 10 million IDR
    
    /**
     * Get material type from attributes
     */
    val material: String
        get() = attributes["Material"]?.firstOrNull() ?: ""
    
    /**
     * Get available colors from attributes
     */
    val availableColors: List<String>
        get() = attributes["Color"] ?: emptyList()
    
    /**
     * Get size from attributes
     */
    val size: String
        get() = attributes["Size"]?.firstOrNull() ?: ""
    
    private fun formatPrice(price: Double): String {
        return "Rp ${String.format("%,.0f", price)}"
    }
}

/**
 * Domain model for Cart Item
 */
@Parcelize
data class CartItem(
    val id: String,
    val product: Product,
    val quantity: Int,
    val selectedColor: String? = null,
    val selectedSize: String? = null,
    val addedAt: Long = System.currentTimeMillis()
) : Parcelable {
    
    /**
     * Get total price for this cart item
     */
    val totalPrice: Double
        get() = (if (product.onSale && product.salePrice != null) product.salePrice else product.price) * quantity
    
    /**
     * Get formatted total price
     */
    val formattedTotalPrice: String
        get() = "Rp ${String.format("%,.0f", totalPrice)}"
    
    /**
     * Check if item has variations selected
     */
    val hasVariations: Boolean
        get() = selectedColor != null || selectedSize != null
}

/**
 * Domain model for Wishlist Item
 */
@Parcelize
data class WishlistItem(
    val id: String,
    val product: Product,
    val addedAt: Long = System.currentTimeMillis()
) : Parcelable

/**
 * Domain model for Product Category
 */
@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val slug: String,
    val description: String = "",
    val image: String = "",
    val count: Int = 0,
    val parent: Int = 0
) : Parcelable

/**
 * Domain model for Product Review
 */
@Parcelize
data class Review(
    val id: Int,
    val productId: Int,
    val reviewer: String,
    val email: String,
    val review: String,
    val rating: Int,
    val dateCreated: String,
    val verified: Boolean,
    val avatarUrl: String = ""
) : Parcelable

/**
 * Domain model for Order
 */
@Parcelize
data class Order(
    val id: Int,
    val orderNumber: String,
    val status: String,
    val total: Double,
    val currency: String,
    val dateCreated: String,
    val dateModified: String,
    val items: List<OrderItem>,
    val billing: Address,
    val shipping: Address,
    val paymentMethod: String,
    val paymentMethodTitle: String,
    val customerNote: String = ""
) : Parcelable {
    
    /**
     * Get formatted total price
     */
    val formattedTotal: String
        get() = "Rp ${String.format("%,.0f", total)}"
    
    /**
     * Get order status display text
     */
    val statusDisplay: String
        get() = when (status) {
            "pending" -> "Pending Payment"
            "processing" -> "Processing"
            "on-hold" -> "On Hold"
            "completed" -> "Completed"
            "cancelled" -> "Cancelled"
            "refunded" -> "Refunded"
            "failed" -> "Failed"
            else -> status.replaceFirstChar { it.uppercase() }
        }
    
    /**
     * Get total items count
     */
    val totalItems: Int
        get() = items.sumOf { it.quantity }
}

/**
 * Domain model for Order Item
 */
@Parcelize
data class OrderItem(
    val id: Int,
    val productId: Int,
    val name: String,
    val quantity: Int,
    val price: Double,
    val total: Double,
    val sku: String,
    val image: String = ""
) : Parcelable {
    
    /**
     * Get formatted price
     */
    val formattedPrice: String
        get() = "Rp ${String.format("%,.0f", price)}"
    
    /**
     * Get formatted total
     */
    val formattedTotal: String
        get() = "Rp ${String.format("%,.0f", total)}"
}

/**
 * Domain model for Address
 */
@Parcelize
data class Address(
    val firstName: String,
    val lastName: String,
    val company: String = "",
    val address1: String,
    val address2: String = "",
    val city: String,
    val state: String,
    val postcode: String,
    val country: String,
    val email: String = "",
    val phone: String = ""
) : Parcelable {
    
    /**
     * Get full name
     */
    val fullName: String
        get() = "$firstName $lastName".trim()
    
    /**
     * Get formatted address
     */
    val formattedAddress: String
        get() = buildString {
            append(address1)
            if (address2.isNotEmpty()) {
                append(", $address2")
            }
            append(", $city")
            if (state.isNotEmpty()) {
                append(", $state")
            }
            append(" $postcode")
            if (country.isNotEmpty()) {
                append(", $country")
            }
        }
}

/**
 * Domain model for User Profile
 */
@Parcelize
data class UserProfile(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val displayName: String,
    val photoUrl: String = "",
    val phoneNumber: String = "",
    val dateCreated: String,
    val billing: Address? = null,
    val shipping: Address? = null
) : Parcelable {
    
    /**
     * Get full name
     */
    val fullName: String
        get() = "$firstName $lastName".trim().ifEmpty { displayName }
    
    /**
     * Get initials for avatar
     */
    val initials: String
        get() = buildString {
            if (firstName.isNotEmpty()) append(firstName.first().uppercase())
            if (lastName.isNotEmpty()) append(lastName.first().uppercase())
        }.ifEmpty { displayName.take(2).uppercase() }
}
