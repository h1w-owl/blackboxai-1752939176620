package com.hayuwidyas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Product cache entity for local storage
 * Stores frequently accessed products for offline access
 */
@Entity(tableName = "product_cache")
data class ProductCacheEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val slug: String,
    val permalink: String,
    val dateCreated: String,
    val type: String,
    val status: String,
    val featured: Boolean,
    val description: String,
    val shortDescription: String,
    val sku: String,
    val price: String,
    val regularPrice: String,
    val salePrice: String,
    val onSale: Boolean,
    val stockQuantity: Int?,
    val stockStatus: String,
    val averageRating: String,
    val ratingCount: Int,
    val categories: List<String>, // JSON string of category names
    val images: List<String>, // JSON string of image URLs
    val variations: List<Int>, // JSON string of variation IDs
    val cachedAt: Long = System.currentTimeMillis()
)

/**
 * Category cache entity for local storage
 */
@Entity(tableName = "category_cache")
data class CategoryCacheEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val slug: String,
    val description: String?,
    val image: String?,
    val count: Int,
    val cachedAt: Long = System.currentTimeMillis()
)