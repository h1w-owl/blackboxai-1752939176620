package com.hayuwidyas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cart item data model for local storage
 */
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    val productId: Int,
    val productName: String,
    val productImage: String,
    val price: String,
    val quantity: Int,
    val userId: String? = null, // null for guest users
    val dateAdded: Long = System.currentTimeMillis()
)

/**
 * Cart request for WooCommerce API
 */
data class CartRequest(
    val product_id: Int,
    val quantity: Int,
    val variation_id: Int? = null,
    val variation: Map<String, String>? = null
)

/**
 * Cart response from WooCommerce API
 */
data class CartResponse(
    val key: String,
    val id: Int,
    val quantity: Int,
    val name: String,
    val title: String,
    val price: String,
    val line_price: String,
    val line_subtotal: String,
    val line_total: String,
    val images: List<ProductImage>,
    val variation: Map<String, String>
)