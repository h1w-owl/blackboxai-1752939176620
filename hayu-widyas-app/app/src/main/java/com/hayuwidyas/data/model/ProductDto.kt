package com.hayuwidyas.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Objects for WooCommerce API responses
 * 
 * These DTOs map directly to the JSON structure returned by WooCommerce REST API
 * for products, categories, and related entities.
 */

data class ProductDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("slug")
    val slug: String,
    
    @SerializedName("permalink")
    val permalink: String,
    
    @SerializedName("date_created")
    val dateCreated: String,
    
    @SerializedName("date_modified")
    val dateModified: String,
    
    @SerializedName("type")
    val type: String,
    
    @SerializedName("status")
    val status: String,
    
    @SerializedName("featured")
    val featured: Boolean,
    
    @SerializedName("catalog_visibility")
    val catalogVisibility: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("short_description")
    val shortDescription: String,
    
    @SerializedName("sku")
    val sku: String?,
    
    @SerializedName("price")
    val price: String,
    
    @SerializedName("regular_price")
    val regularPrice: String,
    
    @SerializedName("sale_price")
    val salePrice: String?,
    
    @SerializedName("on_sale")
    val onSale: Boolean,
    
    @SerializedName("purchasable")
    val purchasable: Boolean,
    
    @SerializedName("total_sales")
    val totalSales: Int,
    
    @SerializedName("virtual")
    val virtual: Boolean,
    
    @SerializedName("downloadable")
    val downloadable: Boolean,
    
    @SerializedName("manage_stock")
    val manageStock: Boolean,
    
    @SerializedName("stock_quantity")
    val stockQuantity: Int?,
    
    @SerializedName("stock_status")
    val stockStatus: String,
    
    @SerializedName("backorders")
    val backorders: String,
    
    @SerializedName("backorders_allowed")
    val backordersAllowed: Boolean,
    
    @SerializedName("backordered")
    val backordered: Boolean,
    
    @SerializedName("sold_individually")
    val soldIndividually: Boolean,
    
    @SerializedName("weight")
    val weight: String?,
    
    @SerializedName("dimensions")
    val dimensions: DimensionsDto,
    
    @SerializedName("shipping_required")
    val shippingRequired: Boolean,
    
    @SerializedName("shipping_taxable")
    val shippingTaxable: Boolean,
    
    @SerializedName("shipping_class")
    val shippingClass: String,
    
    @SerializedName("shipping_class_id")
    val shippingClassId: Int,
    
    @SerializedName("reviews_allowed")
    val reviewsAllowed: Boolean,
    
    @SerializedName("average_rating")
    val averageRating: String,
    
    @SerializedName("rating_count")
    val ratingCount: Int,
    
    @SerializedName("related_ids")
    val relatedIds: List<Int>,
    
    @SerializedName("upsell_ids")
    val upsellIds: List<Int>,
    
    @SerializedName("cross_sell_ids")
    val crossSellIds: List<Int>,
    
    @SerializedName("parent_id")
    val parentId: Int,
    
    @SerializedName("purchase_note")
    val purchaseNote: String,
    
    @SerializedName("categories")
    val categories: List<CategoryDto>,
    
    @SerializedName("tags")
    val tags: List<TagDto>,
    
    @SerializedName("images")
    val images: List<ImageDto>,
    
    @SerializedName("attributes")
    val attributes: List<AttributeDto>,
    
    @SerializedName("default_attributes")
    val defaultAttributes: List<DefaultAttributeDto>,
    
    @SerializedName("variations")
    val variations: List<Int>,
    
    @SerializedName("grouped_products")
    val groupedProducts: List<Int>,
    
    @SerializedName("menu_order")
    val menuOrder: Int,
    
    @SerializedName("meta_data")
    val metaData: List<MetaDataDto>
)

data class DimensionsDto(
    @SerializedName("length")
    val length: String,
    
    @SerializedName("width")
    val width: String,
    
    @SerializedName("height")
    val height: String
)

data class CategoryDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("slug")
    val slug: String
)

data class TagDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("slug")
    val slug: String
)

data class ImageDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("date_created")
    val dateCreated: String,
    
    @SerializedName("date_modified")
    val dateModified: String,
    
    @SerializedName("src")
    val src: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("alt")
    val alt: String
)

data class AttributeDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("position")
    val position: Int,
    
    @SerializedName("visible")
    val visible: Boolean,
    
    @SerializedName("variation")
    val variation: Boolean,
    
    @SerializedName("options")
    val options: List<String>
)

data class DefaultAttributeDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("option")
    val option: String
)

data class MetaDataDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("key")
    val key: String,
    
    @SerializedName("value")
    val value: String
)

/**
 * Response wrapper for paginated product lists
 */
data class ProductsResponse(
    @SerializedName("products")
    val products: List<ProductDto>,
    
    @SerializedName("total")
    val total: Int,
    
    @SerializedName("total_pages")
    val totalPages: Int
)
