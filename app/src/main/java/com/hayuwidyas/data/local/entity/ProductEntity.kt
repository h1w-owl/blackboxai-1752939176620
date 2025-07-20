package com.hayuwidyas.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hayuwidyas.domain.model.Product

/**
 * Room Entity for Product caching
 * 
 * Stores product data locally for offline access and improved performance.
 */
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val shortDescription: String,
    val price: Double,
    val regularPrice: Double,
    val salePrice: Double?,
    val onSale: Boolean,
    val stockStatus: String,
    val stockQuantity: Int?,
    val images: List<String>,
    val categories: List<String>,
    val tags: List<String>,
    val averageRating: Double,
    val ratingCount: Int,
    val featured: Boolean,
    val sku: String,
    val weight: String?,
    val dimensions: String?,
    val attributes: Map<String, List<String>>,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    /**
     * Convert to domain model
     */
    fun toDomainModel(): Product {
        return Product(
            id = id,
            name = name,
            description = description,
            shortDescription = shortDescription,
            price = price,
            regularPrice = regularPrice,
            salePrice = salePrice,
            onSale = onSale,
            stockStatus = stockStatus,
            stockQuantity = stockQuantity,
            images = images,
            categories = categories,
            tags = tags,
            averageRating = averageRating,
            ratingCount = ratingCount,
            featured = featured,
            sku = sku,
            weight = weight,
            dimensions = dimensions,
            attributes = attributes
        )
    }
    
    companion object {
        /**
         * Create from domain model
         */
        fun fromDomainModel(product: Product): ProductEntity {
            return ProductEntity(
                id = product.id,
                name = product.name,
                description = product.description,
                shortDescription = product.shortDescription,
                price = product.price,
                regularPrice = product.regularPrice,
                salePrice = product.salePrice,
                onSale = product.onSale,
                stockStatus = product.stockStatus,
                stockQuantity = product.stockQuantity,
                images = product.images,
                categories = product.categories,
                tags = product.tags,
                averageRating = product.averageRating,
                ratingCount = product.ratingCount,
                featured = product.featured,
                sku = product.sku,
                weight = product.weight,
                dimensions = product.dimensions,
                attributes = product.attributes
            )
        }
    }
}

/**
 * Room Entity for Cart Items
 */
@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey
    val id: String,
    val productId: Int,
    val productName: String,
    val productPrice: Double,
    val productImage: String,
    val quantity: Int,
    val selectedColor: String?,
    val selectedSize: String?,
    val addedAt: Long = System.currentTimeMillis()
)

/**
 * Room Entity for Wishlist Items
 */
@Entity(tableName = "wishlist_items")
data class WishlistItemEntity(
    @PrimaryKey
    val id: String,
    val productId: Int,
    val productName: String,
    val productPrice: Double,
    val productImage: String,
    val addedAt: Long = System.currentTimeMillis()
)
