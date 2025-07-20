package com.hayuwidyas.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hayuwidyas.presentation.navigation.AppNavGraph
import com.hayuwidyas.presentation.theme.HayuWidyasTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity for Hayu Widyas App
 * 
 * Entry point of the application that sets up the navigation graph
 * and applies the luxury brand theme.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        setContent {
            HayuWidyasTheme {
                // Set system UI colors
                SetupSystemUI()
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph()
                }
            }
        }
    }
}

/**
 * Setup system UI colors to match the app theme
 */
@Composable
private fun SetupSystemUI() {
    val systemUiController = rememberSystemUiController()
    
    SideEffect {
        // Set status bar color to white with dark icons
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
        
        // Set navigation bar color to white with dark icons
        systemUiController.setNavigationBarColor(
            color = Color.White,
            darkIcons = true
        )
    }
}
