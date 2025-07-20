package com.hayuwidyas.util

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * WooCommerce Authentication Interceptor
 * 
 * Automatically adds consumer key and secret to all WooCommerce API requests
 * for secure authentication with the backend.
 */
class WooCommerceAuthInterceptor(
    private val consumerKey: String,
    private val consumerSecret: String
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        
        // Add consumer key and secret as query parameters
        val urlWithAuth = originalUrl.newBuilder()
            .addQueryParameter("consumer_key", consumerKey)
            .addQueryParameter("consumer_secret", consumerSecret)
            .build()
        
        val requestWithAuth = originalRequest.newBuilder()
            .url(urlWithAuth)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("User-Agent", "HayuWidyas-Android/1.0")
            .build()
        
        Timber.d("WooCommerce API Request: ${requestWithAuth.method} ${requestWithAuth.url}")
        
        return try {
            val response = chain.proceed(requestWithAuth)
            Timber.d("WooCommerce API Response: ${response.code} for ${requestWithAuth.url}")
            response
        } catch (e: Exception) {
            Timber.e(e, "WooCommerce API Error for ${requestWithAuth.url}")
            throw e
        }
    }
}
