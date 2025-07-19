package com.hayuwidyas.di

import com.hayuwidyas.BuildConfig
import com.hayuwidyas.data.api.WooCommerceService
import com.hayuwidyas.util.WooCommerceAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Network Module for Dependency Injection
 * 
 * Provides network-related dependencies including Retrofit, OkHttp client,
 * and WooCommerce API service with proper authentication.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideWooCommerceAuthInterceptor(): WooCommerceAuthInterceptor {
        return WooCommerceAuthInterceptor(
            consumerKey = BuildConfig.WOOCOMMERCE_CONSUMER_KEY,
            consumerSecret = BuildConfig.WOOCOMMERCE_CONSUMER_SECRET
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: WooCommerceAuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.WOOCOMMERCE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWooCommerceService(retrofit: Retrofit): WooCommerceService {
        return retrofit.create(WooCommerceService::class.java)
    }
}
