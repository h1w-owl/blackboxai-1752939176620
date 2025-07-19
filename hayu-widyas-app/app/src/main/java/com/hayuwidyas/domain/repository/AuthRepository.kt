package com.hayuwidyas.domain.repository

import com.hayuwidyas.domain.model.UserProfile
import com.hayuwidyas.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Authentication Repository Interface
 * 
 * Defines the contract for authentication-related operations.
 * Handles Firebase Auth integration and user profile management.
 */
interface AuthRepository {
    
    /**
     * Get current user as Flow
     */
    fun getCurrentUser(): Flow<UserProfile?>
    
    /**
     * Check if user is logged in
     */
    fun isUserLoggedIn(): Flow<Boolean>
    
    /**
     * Sign in with email and password
     */
    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<UserProfile>
    
    /**
     * Sign up with email and password
     */
    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Resource<UserProfile>
    
    /**
     * Sign in with Google
     */
    suspend fun signInWithGoogle(idToken: String): Resource<UserProfile>
    
    /**
     * Send password reset email
     */
    suspend fun sendPasswordResetEmail(email: String): Resource<Unit>
    
    /**
     * Sign out current user
     */
    suspend fun signOut(): Resource<Unit>
    
    /**
     * Delete user account
     */
    suspend fun deleteAccount(): Resource<Unit>
    
    /**
     * Update user profile
     */
    suspend fun updateUserProfile(
        firstName: String? = null,
        lastName: String? = null,
        phoneNumber: String? = null,
        photoUrl: String? = null
    ): Resource<UserProfile>
    
    /**
     * Update user email
     */
    suspend fun updateUserEmail(newEmail: String): Resource<Unit>
    
    /**
     * Update user password
     */
    suspend fun updateUserPassword(
        currentPassword: String,
        newPassword: String
    ): Resource<Unit>
    
    /**
     * Re-authenticate user (required for sensitive operations)
     */
    suspend fun reAuthenticateUser(password: String): Resource<Unit>
    
    /**
     * Send email verification
     */
    suspend fun sendEmailVerification(): Resource<Unit>
    
    /**
     * Check if email is verified
     */
    suspend fun isEmailVerified(): Boolean
    
    /**
     * Get user profile from Firestore
     */
    suspend fun getUserProfile(userId: String): Resource<UserProfile>
    
    /**
     * Save user profile to Firestore
     */
    suspend fun saveUserProfile(userProfile: UserProfile): Resource<Unit>
    
    /**
     * Update user billing address
     */
    suspend fun updateBillingAddress(
        address: com.hayuwidyas.domain.model.Address
    ): Resource<Unit>
    
    /**
     * Update user shipping address
     */
    suspend fun updateShippingAddress(
        address: com.hayuwidyas.domain.model.Address
    ): Resource<Unit>
    
    /**
     * Get user orders
     */
    suspend fun getUserOrders(
        page: Int = 1,
        perPage: Int = 10
    ): Resource<List<com.hayuwidyas.domain.model.Order>>
    
    /**
     * Create WooCommerce customer account
     */
    suspend fun createWooCommerceCustomer(
        userProfile: UserProfile
    ): Resource<Int> // Returns customer ID
    
    /**
     * Update WooCommerce customer
     */
    suspend fun updateWooCommerceCustomer(
        customerId: Int,
        userProfile: UserProfile
    ): Resource<Unit>
    
    /**
     * Link Firebase user with WooCommerce customer
     */
    suspend fun linkWithWooCommerceCustomer(
        firebaseUserId: String,
        wooCommerceCustomerId: Int
    ): Resource<Unit>
    
    /**
     * Get WooCommerce customer ID for current user
     */
    suspend fun getWooCommerceCustomerId(): Int?
    
    /**
     * Validate user session
     */
    suspend fun validateUserSession(): Resource<Boolean>
    
    /**
     * Refresh user token
     */
    suspend fun refreshUserToken(): Resource<String>
    
    /**
     * Get user preferences
     */
    suspend fun getUserPreferences(): Resource<UserPreferences>
    
    /**
     * Update user preferences
     */
    suspend fun updateUserPreferences(
        preferences: UserPreferences
    ): Resource<Unit>
    
    /**
     * Enable/disable push notifications
     */
    suspend fun updateNotificationSettings(
        enabled: Boolean,
        topics: List<String> = emptyList()
    ): Resource<Unit>
    
    /**
     * Get user activity log
     */
    suspend fun getUserActivityLog(
        limit: Int = 50
    ): Resource<List<UserActivity>>
    
    /**
     * Log user activity
     */
    suspend fun logUserActivity(
        activity: UserActivity
    ): Resource<Unit>
}

/**
 * User preferences data class
 */
data class UserPreferences(
    val language: String = "en",
    val currency: String = "IDR",
    val notifications: NotificationPreferences = NotificationPreferences(),
    val privacy: PrivacyPreferences = PrivacyPreferences(),
    val theme: String = "system", // light, dark, system
    val autoSync: Boolean = true,
    val biometricAuth: Boolean = false
)

/**
 * Notification preferences
 */
data class NotificationPreferences(
    val orderUpdates: Boolean = true,
    val promotions: Boolean = true,
    val newProducts: Boolean = false,
    val priceDrops: Boolean = true,
    val backInStock: Boolean = true,
    val newsletter: Boolean = false,
    val pushNotifications: Boolean = true,
    val emailNotifications: Boolean = true,
    val smsNotifications: Boolean = false
)

/**
 * Privacy preferences
 */
data class PrivacyPreferences(
    val shareDataForAnalytics: Boolean = false,
    val shareDataForMarketing: Boolean = false,
    val allowPersonalization: Boolean = true,
    val showOnlineStatus: Boolean = false,
    val allowLocationTracking: Boolean = false
)

/**
 * User activity data class
 */
data class UserActivity(
    val id: String,
    val userId: String,
    val type: ActivityType,
    val description: String,
    val metadata: Map<String, Any> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis(),
    val ipAddress: String? = null,
    val deviceInfo: String? = null
)

/**
 * Activity types
 */
enum class ActivityType {
    LOGIN,
    LOGOUT,
    REGISTER,
    PASSWORD_RESET,
    PROFILE_UPDATE,
    EMAIL_UPDATE,
    PASSWORD_CHANGE,
    ORDER_PLACED,
    ORDER_CANCELLED,
    PRODUCT_VIEWED,
    PRODUCT_ADDED_TO_CART,
    PRODUCT_ADDED_TO_WISHLIST,
    SEARCH_PERFORMED,
    FILTER_APPLIED,
    PAYMENT_COMPLETED,
    PAYMENT_FAILED,
    REVIEW_SUBMITTED,
    ACCOUNT_DELETED,
    DATA_EXPORTED,
    PRIVACY_SETTINGS_CHANGED,
    NOTIFICATION_SETTINGS_CHANGED
}

/**
 * Authentication states
 */
sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val user: UserProfile) : AuthState()
    data class Error(val message: String) : AuthState()
}

/**
 * Authentication errors
 */
sealed class AuthError : Exception() {
    object InvalidCredentials : AuthError()
    object UserNotFound : AuthError()
    object EmailAlreadyExists : AuthError()
    object WeakPassword : AuthError()
    object InvalidEmail : AuthError()
    object NetworkError : AuthError()
    object TooManyRequests : AuthError()
    object UserDisabled : AuthError()
    object EmailNotVerified : AuthError()
    object RecentLoginRequired : AuthError()
    data class Unknown(val throwable: Throwable) : AuthError()
}
