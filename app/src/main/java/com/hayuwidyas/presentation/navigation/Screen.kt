package com.hayuwidyas.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Screen definitions for navigation
 */
sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
        val arguments: List<NamedNavArgument> = listOf(
            navArgument("productId") { type = NavType.IntType }
        )
    }
}

/**
 * Bottom navigation destinations
 */
enum class BottomNavDestination(
    val route: String,
    val title: String,
    val icon: Int // You would normally use vector icons
) {
    HOME("home", "Home", 0),
    SHOP("shop", "Shop", 0),
    WISHLIST("wishlist", "Wishlist", 0),
    CART("cart", "Cart", 0),
    PROFILE("profile", "Profile", 0)
}