package com.hayuwidyas.di

import android.content.Context
import com.hayuwidyas.data.local.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Database module for dependency injection
 * Provides Room database and DAO instances
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HayuWidyasDatabase {
        return HayuWidyasDatabase.getDatabase(context)
    }
    
    @Provides
    fun provideCartDao(database: HayuWidyasDatabase): CartDao {
        return database.cartDao()
    }
    
    @Provides
    fun provideWishlistDao(database: HayuWidyasDatabase): WishlistDao {
        return database.wishlistDao()
    }
    
    @Provides
    fun provideProductCacheDao(database: HayuWidyasDatabase): ProductCacheDao {
        return database.productCacheDao()
    }
    
    @Provides
    fun provideCategoryCacheDao(database: HayuWidyasDatabase): CategoryCacheDao {
        return database.categoryCacheDao()
    }
}
