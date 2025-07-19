package com.hayuwidyas.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Authentication repository interface for domain layer
 */
interface AuthRepository {
    
    fun getCurrentUser(): Flow<FirebaseUser?>
    
    fun isUserLoggedIn(): Flow<Boolean>
    
    suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<ApiResponse<FirebaseUser>>
    
    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Flow<ApiResponse<FirebaseUser>>
    
    suspend fun signInWithGoogle(idToken: String): Flow<ApiResponse<FirebaseUser>>
    
    suspend fun sendPasswordResetEmail(email: String): Flow<ApiResponse<Unit>>
    
    suspend fun signOut(): ApiResponse<Unit>
    
    suspend fun deleteAccount(): Flow<ApiResponse<Unit>>
    
    suspend fun updateProfile(
        firstName: String?,
        lastName: String?,
        photoUrl: String?
    ): Flow<ApiResponse<Unit>>
    
    suspend fun createWooCommerceCustomer(user: FirebaseUser): Flow<ApiResponse<User>>
    
    suspend fun getWooCommerceCustomer(email: String): Flow<ApiResponse<User>>
    
    suspend fun updateWooCommerceCustomer(
        customerId: String,
        firstName: String?,
        lastName: String?,
        billing: Any?,
        shipping: Any?
    ): Flow<ApiResponse<User>>
}
