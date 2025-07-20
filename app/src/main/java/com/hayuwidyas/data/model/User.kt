package com.hayuwidyas.data.model

import com.google.gson.annotations.SerializedName

/**
 * User data model for Firebase Auth and WooCommerce
 */
data class User(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("first_name")
    val firstName: String,
    
    @SerializedName("last_name")
    val lastName: String,
    
    @SerializedName("username")
    val username: String,
    
    @SerializedName("role")
    val role: String,
    
    @SerializedName("date_created")
    val dateCreated: String,
    
    @SerializedName("date_modified")
    val dateModified: String,
    
    @SerializedName("avatar_url")
    val avatarUrl: String,
    
    @SerializedName("billing")
    val billing: UserBilling?,
    
    @SerializedName("shipping")
    val shipping: UserShipping?,
    
    @SerializedName("is_paying_customer")
    val isPayingCustomer: Boolean,
    
    @SerializedName("orders_count")
    val ordersCount: Int,
    
    @SerializedName("total_spent")
    val totalSpent: String,
    
    @SerializedName("meta_data")
    val metaData: List<UserMetaData>
)

data class UserBilling(
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

data class UserShipping(
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

data class UserMetaData(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("key")
    val key: String,
    
    @SerializedName("value")
    val value: String
)

/**
 * User registration request
 */
data class UserRegistrationRequest(
    val email: String,
    val first_name: String,
    val last_name: String,
    val username: String,
    val password: String
)

/**
 * User update request
 */
data class UserUpdateRequest(
    val first_name: String?,
    val last_name: String?,
    val billing: UserBilling?,
    val shipping: UserShipping?
)