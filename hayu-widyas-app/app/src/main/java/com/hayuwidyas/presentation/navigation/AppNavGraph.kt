package com.hayuwidyas.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hayuwidyas.presentation.ui.MainScreen
import com.hayuwidyas.presentation.ui.auth.LoginScreen
import com.hayuwidyas.presentation.ui.auth.RegisterScreen
import com.hayuwidyas.presentation.ui.onboarding.OnboardingScreen
import com.hayuwidyas.presentation.ui.product.ProductDetailScreen

/**
 * Main navigation graph for the Hayu Widyas app
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        // Onboarding Screen
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToAuth = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Authentication Screens
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onSkip = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        
        // Main App Screen (with bottom navigation)
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToProductDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onNavigateToAuth = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        
        // Product Detail Screen
        composable(
            route = Screen.ProductDetail.route,
            arguments = Screen.ProductDetail.arguments
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                productId = productId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToAuth = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
    }
}