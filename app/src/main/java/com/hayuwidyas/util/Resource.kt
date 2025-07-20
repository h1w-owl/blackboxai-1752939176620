package com.hayuwidyas.util

/**
 * Resource wrapper for handling different states of data operations
 * 
 * Provides a clean way to handle loading, success, and error states
 * throughout the application with proper error messages.
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}

/**
 * UI State sealed class for ViewModels
 * 
 * Represents different UI states with proper error handling
 * and loading indicators for better user experience.
 */
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Empty : UiState<Nothing>()
}

/**
 * Network Result wrapper for API responses
 */
sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val message: String, val code: Int? = null) : NetworkResult<T>()
    data class Exception<T>(val exception: Throwable) : NetworkResult<T>()
}

/**
 * Extension functions for Resource handling
 */
inline fun <T> Resource<T>.onSuccess(action: (value: T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}

inline fun <T> Resource<T>.onError(action: (message: String) -> Unit): Resource<T> {
    if (this is Resource.Error) action(message)
    return this
}

inline fun <T> Resource<T>.onLoading(action: () -> Unit): Resource<T> {
    if (this is Resource.Loading) action()
    return this
}

/**
 * Extension functions for UiState handling
 */
inline fun <T> UiState<T>.onSuccess(action: (value: T) -> Unit): UiState<T> {
    if (this is UiState.Success) action(data)
    return this
}

inline fun <T> UiState<T>.onError(action: (message: String) -> Unit): UiState<T> {
    if (this is UiState.Error) action(message)
    return this
}

inline fun <T> UiState<T>.onLoading(action: () -> Unit): UiState<T> {
    if (this is UiState.Loading) action()
    return this
}

inline fun <T> UiState<T>.onEmpty(action: () -> Unit): UiState<T> {
    if (this is UiState.Empty) action()
    return this
}

/**
 * Convert NetworkResult to Resource
 */
fun <T> NetworkResult<T>.toResource(): Resource<T> {
    return when (this) {
        is NetworkResult.Success -> Resource.Success(data)
        is NetworkResult.Error -> Resource.Error(message)
        is NetworkResult.Exception -> Resource.Error(
            exception.message ?: "Unknown error occurred"
        )
    }
}

/**
 * Convert Resource to UiState
 */
fun <T> Resource<T>.toUiState(): UiState<T> {
    return when (this) {
        is Resource.Success -> {
            if (data != null) {
                // Handle empty lists/collections
                when (data) {
                    is List<*> -> if (data.isEmpty()) UiState.Empty else UiState.Success(data)
                    is Collection<*> -> if (data.isEmpty()) UiState.Empty else UiState.Success(data)
                    else -> UiState.Success(data)
                }
            } else {
                UiState.Empty
            }
        }
        is Resource.Error -> UiState.Error(message ?: "Unknown error occurred")
        is Resource.Loading -> UiState.Loading
    }
}

/**
 * Safe API call wrapper
 */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): NetworkResult<T> {
    return try {
        val result = apiCall()
        NetworkResult.Success(result)
    } catch (throwable: Throwable) {
        when (throwable) {
            is retrofit2.HttpException -> {
                NetworkResult.Error(
                    message = throwable.message() ?: "HTTP Error",
                    code = throwable.code()
                )
            }
            is java.net.UnknownHostException -> {
                NetworkResult.Error("No internet connection")
            }
            is java.net.SocketTimeoutException -> {
                NetworkResult.Error("Request timeout")
            }
            is java.net.ConnectException -> {
                NetworkResult.Error("Connection failed")
            }
            else -> {
                NetworkResult.Exception(throwable)
            }
        }
    }
}

/**
 * Error message constants
 */
object ErrorMessages {
    const val NETWORK_ERROR = "Network error. Please check your connection."
    const val SERVER_ERROR = "Server error. Please try again later."
    const val UNKNOWN_ERROR = "Something went wrong. Please try again."
    const val NO_INTERNET = "No internet connection available."
    const val TIMEOUT_ERROR = "Request timeout. Please try again."
    const val PRODUCT_NOT_FOUND = "Product not found."
    const val CART_EMPTY = "Your cart is empty."
    const val WISHLIST_EMPTY = "Your wishlist is empty."
    const val LOGIN_REQUIRED = "Please login to continue."
    const val INVALID_CREDENTIALS = "Invalid email or password."
    const val EMAIL_ALREADY_EXISTS = "Email already exists."
    const val WEAK_PASSWORD = "Password should be at least 6 characters."
    const val INVALID_EMAIL = "Please enter a valid email address."
    const val REQUIRED_FIELD = "This field is required."
    const val ORDER_FAILED = "Failed to place order. Please try again."
    const val PAYMENT_FAILED = "Payment failed. Please try again."
}
