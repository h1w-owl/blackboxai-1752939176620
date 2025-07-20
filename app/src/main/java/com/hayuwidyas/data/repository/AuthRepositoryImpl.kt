package com.hayuwidyas.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.data.model.User
import com.hayuwidyas.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    
    override fun getCurrentUser(): Flow<FirebaseUser?> = flow {
        emit(firebaseAuth.currentUser)
    }
    
    override fun isUserLoggedIn(): Flow<Boolean> = flow {
        emit(firebaseAuth.currentUser != null)
    }
    
    override suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<ApiResponse<FirebaseUser>> = flow {
        emit(ApiResponse.Loading())
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user?.let { user ->
                emit(ApiResponse.Success(user))
            } ?: emit(ApiResponse.Error("Sign in failed"))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Sign in failed: ${e.message}"))
        }
    }
    
    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Flow<ApiResponse<FirebaseUser>> = flow {
        emit(ApiResponse.Loading())
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { user ->
                emit(ApiResponse.Success(user))
            } ?: emit(ApiResponse.Error("Sign up failed"))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Sign up failed: ${e.message}"))
        }
    }
    
    override suspend fun signInWithGoogle(idToken: String): Flow<ApiResponse<FirebaseUser>> = flow {
        emit(ApiResponse.Error("Google sign in not implemented yet"))
    }
    
    override suspend fun sendPasswordResetEmail(email: String): Flow<ApiResponse<Unit>> = flow {
        emit(ApiResponse.Loading())
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(ApiResponse.Success(Unit))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Failed to send reset email: ${e.message}"))
        }
    }
    
    override suspend fun signOut(): ApiResponse<Unit> {
        return try {
            firebaseAuth.signOut()
            ApiResponse.Success(Unit)
        } catch (e: Exception) {
            ApiResponse.Error("Sign out failed: ${e.message}")
        }
    }
    
    override suspend fun deleteAccount(): Flow<ApiResponse<Unit>> = flow {
        emit(ApiResponse.Loading())
        try {
            firebaseAuth.currentUser?.delete()?.await()
            emit(ApiResponse.Success(Unit))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Failed to delete account: ${e.message}"))
        }
    }
    
    override suspend fun updateProfile(
        firstName: String?,
        lastName: String?,
        photoUrl: String?
    ): Flow<ApiResponse<Unit>> = flow {
        emit(ApiResponse.Success(Unit))
    }
    
    override suspend fun createWooCommerceCustomer(user: FirebaseUser): Flow<ApiResponse<User>> = flow {
        emit(ApiResponse.Error("WooCommerce customer creation not implemented yet"))
    }
    
    override suspend fun getWooCommerceCustomer(email: String): Flow<ApiResponse<User>> = flow {
        emit(ApiResponse.Error("WooCommerce customer retrieval not implemented yet"))
    }
    
    override suspend fun updateWooCommerceCustomer(
        customerId: String,
        firstName: String?,
        lastName: String?,
        billing: Any?,
        shipping: Any?
    ): Flow<ApiResponse<User>> = flow {
        emit(ApiResponse.Error("WooCommerce customer update not implemented yet"))
    }
}