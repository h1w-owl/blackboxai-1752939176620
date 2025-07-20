package com.hayuwidyas

import android.app.Application
import androidx.startup.AppInitializer
import dagger.hilt.android.HiltAndroidApp
// Note: Add timber dependency to build.gradle for logging
// import timber.log.Timber

/**
 * Hayu Widyas Application Class
 * 
 * This is the main application class that initializes Hilt dependency injection
 * and other app-wide configurations for the luxury leather bag e-commerce app.
 */
@HiltAndroidApp
class HayuWidyasApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Initialize logging for debug builds
        // if (BuildConfig.DEBUG) {
        //     Timber.plant(Timber.DebugTree())
        // }
        
        // Initialize app startup components
        AppInitializer.getInstance(this)
            .initializeComponent(AppStartupInitializer::class.java)
        
        // Timber.d("Hayu Widyas Application initialized")
    }
}

/**
 * App Startup Initializer for early initialization of critical components
 */
class AppStartupInitializer : androidx.startup.Initializer<Unit> {
    
    override fun create(context: android.content.Context) {
        // Initialize any components that need early startup
        // App startup components initialized
    }
    
    override fun dependencies(): List<Class<out androidx.startup.Initializer<*>>> {
        return emptyList()
    }
}
