package com.hayuwidyas.di

import android.content.Context
import androidx.room.Room
import com.hayuwidyas.data.local.AppDatabase
import com.hayuwidyas.data.local.dao.CartDao
import com.hayuwidyas.data.local.dao.ProductDao
import com.hayuwidyas.data.local.dao.WishlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Database Module for Dependency Injection
 * 
 * Provides Room database instance and all DAOs for local data storage.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    fun provideWishlistDao(database: AppDatabase): WishlistDao {
        return database.wishlistDao()
    }
}
