package com.hayuwidyas.data.model

import com.google.gson.annotations.SerializedName

/**
 * Order data model for WooCommerce API
 */
data class Order(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("parent_id")
    val parentId: Int,
    
    @SerializedName("status")
    val status: String,
    
    @SerializedName("currency")
    val currency: String,
    
    @SerializedName("version")
    val version: String,
    
    @SerializedName("prices_include_tax")
    val pricesIncludeTax: Boolean,
    
    @SerializedName("date_created")
    val dateCreated: String,
    
    @SerializedName("date_modified")
    val dateModified: String,
    
    @SerializedName("discount_total")
    val discountTotal: String,
    
    @SerializedName("discount_tax")
    val discountTax: String,
    
    @SerializedName("shipping_total")
    val shippingTotal: String,
    
    @SerializedName("shipping_tax")
    val shippingTax: String,
    
    @SerializedName("cart_tax")
    val cartTax: String,
    
    @SerializedName("total")
    val total: String,
    
    @SerializedName("total_tax")
    val totalTax: String,
    
    @SerializedName("customer_id")
    val customerId: Int,
    
    @SerializedName("order_key")
    val orderKey: String,
    
    @SerializedName("billing")
    val billing: OrderBilling,
    
    @SerializedName("shipping")
    val shipping: OrderShipping,
    
    @SerializedName("payment_method")
    val paymentMethod: String,
    
    @SerializedName("payment_method_title")
    val paymentMethodTitle: String,
    
    @SerializedName("transaction_id")
    val transactionId: String,
    
    @SerializedName("customer_ip_address")
    val customerIpAddress: String,
    
    @SerializedName("customer_user_agent")
    val customerUserAgent: String,
    
    @SerializedName("created_via")
    val createdVia: String,
    
    @SerializedName("customer_note")
    val customerNote: String,
    
    @SerializedName("date_completed")
    val dateCompleted: String?,
    
    @SerializedName("date_paid")
    val datePaid: String?,
    
    @SerializedName("cart_hash")
    val cartHash: String,
    
    @SerializedName("number")
    val number: String,
    
    @SerializedName("meta_data")
    val metaData: List<OrderMetaData>,
    
    @SerializedName("line_items")
    val lineItems: List<OrderLineItem>,
    
    @SerializedName("tax_lines")
    val taxLines: List<OrderTaxLine>,
    
    @SerializedName("shipping_lines")
    val shippingLines: List<OrderShippingLine>,
    
    @SerializedName("fee_lines")
    val feeLines: List<OrderFeeLine>,
    
    @SerializedName("coupon_lines")
    val couponLines: List<OrderCouponLine>
)

data class OrderBilling(
    @SerializedName("first_name")
    val firstName: String,
    
    @SerializedName("last_name")
    val lastName: String,
    
    @SerializedName("company")
    val company: String,
    
    @SerializedName("address_1")
    val address1: String,
    
    @SerializedName("address_2")
    val address2: String,
    
    @SerializedName("city")
    val city: String,
    
    @SerializedName("state")
    val state: String,
    
    @SerializedName("postcode")
    val postcode: String,
    
    @SerializedName("country")
    val country: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("phone")
    val phone: String
)

data class OrderShipping(
    @SerializedName("first_name")
    val firstName: String,
    
    @SerializedName("last_name")
    val lastName: String,
    
    @SerializedName("company")
    val company: String,
    
    @SerializedName("address_1")
    val address1: String,
    
    @SerializedName("address_2")
    val address2: String,
    
    @SerializedName("city")
    val city: String,
    
    @SerializedName("state")
    val state: String,
    
    @SerializedName("postcode")
    val postcode: String,
    
    @SerializedName("country")
    val country: String
)

data class OrderMetaData(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("key")
    val key: String,
    
    @SerializedName("value")
    val value: String
)

data class OrderLineItem(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("product_id")
    val productId: Int,
    
    @SerializedName("variation_id")
    val variationId: Int,
    
    @SerializedName("quantity")
    val quantity: Int,
    
    @SerializedName("tax_class")
    val taxClass: String,
    
    @SerializedName("subtotal")
    val subtotal: String,
    
    @SerializedName("subtotal_tax")
    val subtotalTax: String,
    
    @SerializedName("total")
    val total: String,
    
    @SerializedName("total_tax")
    val totalTax: String,
    
    @SerializedName("taxes")
    val taxes: List<OrderTax>,
    
    @SerializedName("meta_data")
    val metaData: List<OrderMetaData>,
    
    @SerializedName("sku")
    val sku: String,
    
    @SerializedName("price")
    val price: String,
    
    @SerializedName("image")
    val image: ProductImage?,
    
    @SerializedName("parent_name")
    val parentName: String?
)

data class OrderTax(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("total")
    val total: String,
    
    @SerializedName("subtotal")
    val subtotal: String
)

data class OrderTaxLine(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("rate_code")
    val rateCode: String,
    
    @SerializedName("rate_id")
    val rateId: Int,
    
    @SerializedName("label")
    val label: String,
    
    @SerializedName("compound")
    val compound: Boolean,
    
    @SerializedName("tax_total")
    val taxTotal: String,
    
    @SerializedName("shipping_tax_total")
    val shippingTaxTotal: String,
    
    @SerializedName("rate_percent")
    val ratePercent: Int,
    
    @SerializedName("meta_data")
    val metaData: List<OrderMetaData>
)

data class OrderShippingLine(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("method_title")
    val methodTitle: String,
    
    @SerializedName("method_id")
    val methodId: String,
    
    @SerializedName("instance_id")
    val instanceId: String,
    
    @SerializedName("total")
    val total: String,
    
    @SerializedName("total_tax")
    val totalTax: String,
    
    @SerializedName("taxes")
    val taxes: List<OrderTax>,
    
    @SerializedName("meta_data")
    val metaData: List<OrderMetaData>
)

data class OrderFeeLine(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("tax_class")
    val taxClass: String,
    
    @SerializedName("tax_status")
    val taxStatus: String,
    
    @SerializedName("total")
    val total: String,
    
    @SerializedName("total_tax")
    val totalTax: String,
    
    @SerializedName("taxes")
    val taxes: List<OrderTax>,
    
    @SerializedName("meta_data")
    val metaData: List<OrderMetaData>
)

data class OrderCouponLine(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("code")
    val code: String,
    
    @SerializedName("discount")
    val discount: String,
    
    @SerializedName("discount_tax")
    val discountTax: String,
    
    @SerializedName("meta_data")
    val metaData: List<OrderMetaData>
)

/**
 * Order creation request
 */
data class CreateOrderRequest(
    val payment_method: String,
    val payment_method_title: String,
    val set_paid: Boolean,
    val billing: OrderBilling,
    val shipping: OrderShipping,
    val line_items: List<CreateOrderLineItem>,
    val shipping_lines: List<CreateOrderShippingLine>
)

data class CreateOrderLineItem(
    val product_id: Int,
    val quantity: Int,
    val variation_id: Int? = null
)

data class CreateOrderShippingLine(
    val method_id: String,
    val method_title: String,
    val total: String
)