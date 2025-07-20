package com.hayuwidyas.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.hayuwidyas.data.model.CartItem
import com.hayuwidyas.data.model.WishlistItem

/**
 * Room Database for Hayu Widyas App
 * Handles local storage for cart items, wishlist, and other cached data
 */
@Database(
    entities = [
        CartItem::class,
        WishlistItem::class,
        ProductCacheEntity::class,
        CategoryCacheEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class HayuWidyasDatabase : RoomDatabase() {
    
    abstract fun cartDao(): CartDao
    abstract fun wishlistDao(): WishlistDao
    abstract fun productCacheDao(): ProductCacheDao
    abstract fun categoryCacheDao(): CategoryCacheDao
    
    companion object {
        @Volatile
        private var INSTANCE: HayuWidyasDatabase? = null
        
        private const val DATABASE_NAME = "hayu_widyas_database"
        
        fun getDatabase(context: Context): HayuWidyasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HayuWidyasDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}