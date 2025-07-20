package com.hayuwidyas.util

/**
 * A generic wrapper for handling success and error states
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

/**
 * Extension functions for Result handling
 */
inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T> Result<T>.onError(action: (exception: Throwable) -> Unit): Result<T> {
    if (this is Result.Error) action(exception)
    return this
}

inline fun <T> Result<T>.onLoading(action: () -> Unit): Result<T> {
    if (this is Result.Loading) action()
    return this
}

/**
 * Returns the encapsulated data if this instance represents success
 * or null if it is error or loading.
 */
fun <T> Result<T>.getOrNull(): T? {
    return when (this) {
        is Result.Success -> data
        else -> null
    }
}

/**
 * Returns the encapsulated data if this instance represents success
 * or the defaultValue if it is error or loading.
 */
fun <T> Result<T>.getOrDefault(defaultValue: T): T {
    return when (this) {
        is Result.Success -> data
        else -> defaultValue
    }
}

/**
 * Returns the encapsulated exception if this instance represents error
 * or null if it is success or loading.
 */
fun <T> Result<T>.exceptionOrNull(): Throwable? {
    return when (this) {
        is Result.Error -> exception
        else -> null
    }
}

/**
 * Returns true if this instance represents a successful outcome.
 */
fun <T> Result<T>.isSuccess(): Boolean = this is Result.Success

/**
 * Returns true if this instance represents an error outcome.
 */
fun <T> Result<T>.isError(): Boolean = this is Result.Error

/**
 * Returns true if this instance represents a loading state.
 */
fun <T> Result<T>.isLoading(): Boolean = this is Result.Loading

/**
 * Transform the data if success, otherwise return the error or loading state
 */
inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> this
        is Result.Loading -> this
    }
}

/**
 * Transform the data if success using suspending function
 */
inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
    return when (this) {
        is Result.Success -> transform(data)
        is Result.Error -> this
        is Result.Loading -> this
    }
}

/**
 * Utility to wrap suspend function calls in Result
 */
suspend inline fun <T> safeCall(action: suspend () -> T): Result<T> {
    return try {
        Result.Success(action())
    } catch (e: Exception) {
        Result.Error(e)
    }
}