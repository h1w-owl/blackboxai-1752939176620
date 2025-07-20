package com.hayuwidyas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Wishlist item data model for local storage
 */
@Entity(tableName = "wishlist_items")
data class WishlistItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    val productId: Int,
    val productName: String,
    val productImage: String,
    val price: String,
    val regularPrice: String,
    val salePrice: String,
    val onSale: Boolean,
    val userId: String? = null, // null for guest users
    val dateAdded: Long = System.currentTimeMillis()
)