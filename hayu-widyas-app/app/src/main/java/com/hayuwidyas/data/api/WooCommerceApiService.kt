package com.hayuwidyas.data.api

import com.hayuwidyas.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * WooCommerce REST API service interface
 * Provides endpoints for products, orders, customers, and other e-commerce operations
 */
interface WooCommerceApiService {
    
    // PRODUCTS
    @GET("products")
    suspend fun getProducts(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc",
        @Query("category") category: String? = null,
        @Query("min_price") minPrice: String? = null,
        @Query("max_price") maxPrice: String? = null,
        @Query("search") search: String? = null,
        @Query("status") status: String = "publish",
        @Query("featured") featured: Boolean? = null
    ): Response<List<Product>>
    
    @GET("products/{id}")
    suspend fun getProduct(@Path("id") productId: Int): Response<Product>
    
    @GET("products/categories")
    suspend fun getProductCategories(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 100,
        @Query("orderby") orderBy: String = "name",
        @Query("order") order: String = "asc"
    ): Response<List<ProductCategory>>
    
    @GET("products/categories/{id}")
    suspend fun getProductCategory(@Path("id") categoryId: Int): Response<ProductCategory>
    
    // ORDERS
    @GET("orders")
    suspend fun getOrders(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc",
        @Query("status") status: String? = null,
        @Query("customer") customerId: Int? = null
    ): Response<List<Order>>
    
    @GET("orders/{id}")
    suspend fun getOrder(@Path("id") orderId: Int): Response<Order>
    
    @POST("orders")
    suspend fun createOrder(@Body order: CreateOrderRequest): Response<Order>
    
    @PUT("orders/{id}")
    suspend fun updateOrder(
        @Path("id") orderId: Int,
        @Body order: Map<String, Any>
    ): Response<Order>
    
    @DELETE("orders/{id}")
    suspend fun deleteOrder(@Path("id") orderId: Int): Response<Order>
    
    // CUSTOMERS
    @GET("customers")
    suspend fun getCustomers(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("orderby") orderBy: String = "date_registered",
        @Query("order") order: String = "desc",
        @Query("search") search: String? = null,
        @Query("email") email: String? = null
    ): Response<List<User>>
    
    @GET("customers/{id}")
    suspend fun getCustomer(@Path("id") customerId: String): Response<User>
    
    @POST("customers")
    suspend fun createCustomer(@Body customer: UserRegistrationRequest): Response<User>
    
    @PUT("customers/{id}")
    suspend fun updateCustomer(
        @Path("id") customerId: String,
        @Body customer: UserUpdateRequest
    ): Response<User>
    
    @DELETE("customers/{id}")
    suspend fun deleteCustomer(@Path("id") customerId: String): Response<User>
    
    // CART (Custom endpoints - may need additional WooCommerce plugins)
    @POST("cart/add-item")
    suspend fun addToCart(@Body cartRequest: CartRequest): Response<CartResponse>
    
    @GET("cart")
    suspend fun getCart(@Query("cart_key") cartKey: String? = null): Response<List<CartResponse>>
    
    @PUT("cart/item")
    suspend fun updateCartItem(
        @Query("cart_key") cartKey: String,
        @Query("cart_item_key") cartItemKey: String,
        @Body cartRequest: CartRequest
    ): Response<CartResponse>
    
    @DELETE("cart/item")
    suspend fun removeFromCart(
        @Query("cart_key") cartKey: String,
        @Query("cart_item_key") cartItemKey: String
    ): Response<Map<String, Any>>
    
    @DELETE("cart/clear")
    suspend fun clearCart(@Query("cart_key") cartKey: String): Response<Map<String, Any>>
    
    // SYSTEM
    @GET("system_status")
    suspend fun getSystemStatus(): Response<Map<String, Any>>
    
    @GET("data/countries")
    suspend fun getCountries(): Response<Map<String, String>>
    
    @GET("data/countries/{country_code}")
    suspend fun getCountryStates(@Path("country_code") countryCode: String): Response<Map<String, String>>
    
    @GET("shipping/zones")
    suspend fun getShippingZones(): Response<List<Map<String, Any>>>
    
    @GET("payment_gateways")
    suspend fun getPaymentGateways(): Response<List<Map<String, Any>>>
    
    @GET("tax/rates")
    suspend fun getTaxRates(): Response<List<Map<String, Any>>>
    
    // COUPONS
    @GET("coupons")
    suspend fun getCoupons(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("search") search: String? = null
    ): Response<List<Map<String, Any>>>
    
    @GET("coupons/{id}")
    suspend fun getCoupon(@Path("id") couponId: Int): Response<Map<String, Any>>
    
    @POST("coupons/batch")
    suspend fun validateCoupon(@Body couponData: Map<String, Any>): Response<Map<String, Any>>
    
    // REVIEWS
    @GET("products/reviews")
    suspend fun getProductReviews(
        @Query("product") productId: Int? = null,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): Response<List<Map<String, Any>>>
    
    @POST("products/reviews")
    suspend fun createProductReview(@Body review: Map<String, Any>): Response<Map<String, Any>>
    
    // REPORTS
    @GET("reports/sales")
    suspend fun getSalesReport(
        @Query("period") period: String = "week",
        @Query("date_min") dateMin: String? = null,
        @Query("date_max") dateMax: String? = null
    ): Response<List<Map<String, Any>>>
    
    @GET("reports/top_sellers")
    suspend fun getTopSellers(
        @Query("period") period: String = "week",
        @Query("date_min") dateMin: String? = null,
        @Query("date_max") dateMax: String? = null
    ): Response<List<Map<String, Any>>>
}