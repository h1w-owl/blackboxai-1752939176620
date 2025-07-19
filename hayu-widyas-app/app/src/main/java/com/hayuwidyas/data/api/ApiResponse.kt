package com.hayuwidyas.data.api

/**
 * Generic API response wrapper for handling different states
 */
sealed class ApiResponse<T> {
    class Loading<T> : ApiResponse<T>()
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val message: String, val throwable: Throwable? = null) : ApiResponse<T>()
}

/**
 * Extension function to handle API responses
 */
inline fun <T> ApiResponse<T>.onSuccess(action: (value: T) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Success) action(data)
    return this
}

inline fun <T> ApiResponse<T>.onError(action: (message: String, throwable: Throwable?) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Error) action(message, throwable)
    return this
}

inline fun <T> ApiResponse<T>.onLoading(action: () -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Loading) action()
    return this
}

/**
 * Map ApiResponse to another type
 */
inline fun <T, R> ApiResponse<T>.map(transform: (T) -> R): ApiResponse<R> {
    return when (this) {
        is ApiResponse.Loading -> ApiResponse.Loading()
        is ApiResponse.Success -> ApiResponse.Success(transform(data))
        is ApiResponse.Error -> ApiResponse.Error(message, throwable)
    }
}

/**
 * Flat map ApiResponse to another ApiResponse
 */
inline fun <T, R> ApiResponse<T>.flatMap(transform: (T) -> ApiResponse<R>): ApiResponse<R> {
    return when (this) {
        is ApiResponse.Loading -> ApiResponse.Loading()
        is ApiResponse.Success -> transform(data)
        is ApiResponse.Error -> ApiResponse.Error(message, throwable)
    }
}