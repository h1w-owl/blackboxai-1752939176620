package com.hayuwidyas.di

import com.hayuwidyas.data.repository.AuthRepositoryImpl
import com.hayuwidyas.data.repository.CartRepositoryImpl
import com.hayuwidyas.data.repository.ProductRepositoryImpl
import com.hayuwidyas.data.repository.WishlistRepositoryImpl
import com.hayuwidyas.domain.repository.AuthRepository
import com.hayuwidyas.domain.repository.CartRepository
import com.hayuwidyas.domain.repository.ProductRepository
import com.hayuwidyas.domain.repository.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Repository Module for Dependency Injection
 * 
 * Binds repository interfaces to their implementations.
 * This follows the dependency inversion principle of Clean Architecture.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

    @Binds
    @Singleton
    abstract fun bindWishlistRepository(
        wishlistRepositoryImpl: WishlistRepositoryImpl
    ): WishlistRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
