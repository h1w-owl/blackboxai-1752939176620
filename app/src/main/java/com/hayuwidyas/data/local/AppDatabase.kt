package com.hayuwidyas.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.hayuwidyas.data.local.dao.CartDao
import com.hayuwidyas.data.local.dao.WishlistDao
import com.hayuwidyas.data.local.dao.ProductDao
import com.hayuwidyas.data.local.entity.CartItemEntity
import com.hayuwidyas.data.local.entity.WishlistItemEntity
import com.hayuwidyas.data.local.entity.ProductEntity
import com.hayuwidyas.data.local.converter.Converters

/**
 * Room Database for Hayu Widyas App
 * 
 * Local database for caching products, cart items, wishlist items,
 * and other offline data to provide seamless user experience.
 */
@Database(
    entities = [
        ProductEntity::class,
        CartItemEntity::class,
        WishlistItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun wishlistDao(): WishlistDao
    
    companion object {
        const val DATABASE_NAME = "hayu_widyas_database"
        
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
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
