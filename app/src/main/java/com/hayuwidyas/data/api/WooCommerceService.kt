package com.hayuwidyas.data.api

import com.hayuwidyas.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * WooCommerce REST API Service Interface
 * 
 * Defines all API endpoints for interacting with the Hayu Widyas WooCommerce store.
 * Includes products, orders, categories, and customer management.
 */
interface WooCommerceService {

    // ==================== PRODUCTS ====================
    
    /**
     * Get all products with pagination and filtering
     */
    @GET("products")
    suspend fun getProducts(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("search") search: String? = null,
        @Query("category") category: String? = null,
        @Query("tag") tag: String? = null,
        @Query("featured") featured: Boolean? = null,
        @Query("on_sale") onSale: Boolean? = null,
        @Query("min_price") minPrice: String? = null,
        @Query("max_price") maxPrice: String? = null,
        @Query("status") status: String = "publish",
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc",
        @Query("stock_status") stockStatus: String? = null
    ): Response<List<ProductDto>>

    /**
     * Get a single product by ID
     */
    @GET("products/{id}")
    suspend fun getProduct(@Path("id") productId: Int): Response<ProductDto>

    /**
     * Get product variations
     */
    @GET("products/{id}/variations")
    suspend fun getProductVariations(
        @Path("id") productId: Int,
        @Query("per_page") perPage: Int = 100
    ): Response<List<ProductDto>>

    /**
     * Get product reviews
     */
    @GET("products/reviews")
    suspend fun getProductReviews(
        @Query("product") productId: Int,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): Response<List<ReviewDto>>

    // ==================== CATEGORIES ====================
    
    /**
     * Get all product categories
     */
    @GET("products/categories")
    suspend fun getCategories(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 100,
        @Query("hide_empty") hideEmpty: Boolean = true,
        @Query("parent") parent: Int? = null,
        @Query("orderby") orderBy: String = "name",
        @Query("order") order: String = "asc"
    ): Response<List<CategoryDto>>

    /**
     * Get a single category by ID
     */
    @GET("products/categories/{id}")
    suspend fun getCategory(@Path("id") categoryId: Int): Response<CategoryDto>

    // ==================== ORDERS ====================
    
    /**
     * Create a new order
     */
    @POST("orders")
    suspend fun createOrder(@Body order: CreateOrderRequest): Response<OrderDto>

    /**
     * Get all orders for a customer
     */
    @GET("orders")
    suspend fun getOrders(
        @Query("customer") customerId: Int? = null,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("status") status: String? = null,
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc"
    ): Response<List<OrderDto>>

    /**
     * Get a single order by ID
     */
    @GET("orders/{id}")
    suspend fun getOrder(@Path("id") orderId: Int): Response<OrderDto>

    /**
     * Update an order
     */
    @PUT("orders/{id}")
    suspend fun updateOrder(
        @Path("id") orderId: Int,
        @Body order: UpdateOrderRequest
    ): Response<OrderDto>

    // ==================== CUSTOMERS ====================
    
    /**
     * Create a new customer
     */
    @POST("customers")
    suspend fun createCustomer(@Body customer: CreateCustomerRequest): Response<CustomerDto>

    /**
     * Get customer by ID
     */
    @GET("customers/{id}")
    suspend fun getCustomer(@Path("id") customerId: Int): Response<CustomerDto>

    /**
     * Update customer
     */
    @PUT("customers/{id}")
    suspend fun updateCustomer(
        @Path("id") customerId: Int,
        @Body customer: UpdateCustomerRequest
    ): Response<CustomerDto>

    // ==================== COUPONS ====================
    
    /**
     * Get all coupons
     */
    @GET("coupons")
    suspend fun getCoupons(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("search") search: String? = null
    ): Response<List<CouponDto>>

    /**
     * Get coupon by code
     */
    @GET("coupons")
    suspend fun getCouponByCode(@Query("code") code: String): Response<List<CouponDto>>

    // ==================== SHIPPING ====================
    
    /**
     * Get shipping zones
     */
    @GET("shipping/zones")
    suspend fun getShippingZones(): Response<List<ShippingZoneDto>>

    /**
     * Get shipping methods for a zone
     */
    @GET("shipping/zones/{id}/methods")
    suspend fun getShippingMethods(@Path("id") zoneId: Int): Response<List<ShippingMethodDto>>

    // ==================== PAYMENT GATEWAYS ====================
    
    /**
     * Get available payment gateways
     */
    @GET("payment_gateways")
    suspend fun getPaymentGateways(): Response<List<PaymentGatewayDto>>

    // ==================== SYSTEM STATUS ====================
    
    /**
     * Get system status (for health checks)
     */
    @GET("system_status")
    suspend fun getSystemStatus(): Response<SystemStatusDto>
}

// ==================== ADDITIONAL DTOs ====================

data class ReviewDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("date_created")
    val dateCreated: String,
    
    @SerializedName("review")
    val review: String,
    
    @SerializedName("rating")
    val rating: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("verified")
    val verified: Boolean,
    
    @SerializedName("reviewer_avatar_urls")
    val reviewerAvatarUrls: Map<String, String>,
    
    @SerializedName("product_id")
    val productId: Int,
    
    @SerializedName("status")
    val status: String
)

data class CustomerDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("date_created")
    val dateCreated: String,
    
    @SerializedName("date_modified")
    val dateModified: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("first_name")
    val firstName: String,
    
    @SerializedName("last_name")
    val lastName: String,
    
    @SerializedName("role")
    val role: String,
    
    @SerializedName("username")
    val username: String,
    
    @SerializedName("billing")
    val billing: BillingDto,
    
    @SerializedName("shipping")
    val shipping: ShippingDto,
    
    @SerializedName("is_paying_customer")
    val isPayingCustomer: Boolean,
    
    @SerializedName("avatar_url")
    val avatarUrl: String,
    
    @SerializedName("meta_data")
    val metaData: List<MetaDataDto>
)

data class CreateCustomerRequest(
    @SerializedName("email")
    val email: String,
    
    @SerializedName("first_name")
    val firstName: String,
    
    @SerializedName("last_name")
    val lastName: String,
    
    @SerializedName("username")
    val username: String? = null,
    
    @SerializedName("password")
    val password: String? = null,
    
    @SerializedName("billing")
    val billing: BillingDto? = null,
    
    @SerializedName("shipping")
    val shipping: ShippingDto? = null
)

data class UpdateCustomerRequest(
    @SerializedName("first_name")
    val firstName: String? = null,
    
    @SerializedName("last_name")
    val lastName: String? = null,
    
    @SerializedName("billing")
    val billing: BillingDto? = null,
    
    @SerializedName("shipping")
    val shipping: ShippingDto? = null
)

data class UpdateOrderRequest(
    @SerializedName("status")
    val status: String? = null,
    
    @SerializedName("customer_note")
    val customerNote: String? = null
)

data class CouponDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("code")
    val code: String,
    
    @SerializedName("amount")
    val amount: String,
    
    @SerializedName("discount_type")
    val discountType: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("date_expires")
    val dateExpires: String?,
    
    @SerializedName("usage_count")
    val usageCount: Int,
    
    @SerializedName("individual_use")
    val individualUse: Boolean,
    
    @SerializedName("usage_limit")
    val usageLimit: Int?,
    
    @SerializedName("minimum_amount")
    val minimumAmount: String,
    
    @SerializedName("maximum_amount")
    val maximumAmount: String
)

data class ShippingZoneDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("order")
    val order: Int
)

data class ShippingMethodDto(
    @SerializedName("instance_id")
    val instanceId: Int,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("order")
    val order: Int,
    
    @SerializedName("enabled")
    val enabled: Boolean,
    
    @SerializedName("method_id")
    val methodId: String,
    
    @SerializedName("method_title")
    val methodTitle: String,
    
    @SerializedName("method_description")
    val methodDescription: String,
    
    @SerializedName("settings")
    val settings: Map<String, Any>
)

data class PaymentGatewayDto(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("order")
    val order: Int,
    
    @SerializedName("enabled")
    val enabled: Boolean,
    
    @SerializedName("method_title")
    val methodTitle: String,
    
    @SerializedName("method_description")
    val methodDescription: String,
    
    @SerializedName("method_supports")
    val methodSupports: List<String>,
    
    @SerializedName("settings")
    val settings: Map<String, Any>
)

data class SystemStatusDto(
    @SerializedName("environment")
    val environment: Map<String, Any>,
    
    @SerializedName("database")
    val database: Map<String, Any>,
    
    @SerializedName("active_plugins")
    val activePlugins: List<Map<String, Any>>,
    
    @SerializedName("theme")
    val theme: Map<String, Any>,
    
    @SerializedName("settings")
    val settings: Map<String, Any>
)
