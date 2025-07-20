package com.hayuwidyas.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hayuwidyas.presentation.navigation.BottomNavDestination
import com.hayuwidyas.presentation.ui.home.HomeScreen
import com.hayuwidyas.presentation.ui.shop.ShopScreen
import com.hayuwidyas.presentation.ui.wishlist.WishlistScreen
import com.hayuwidyas.presentation.ui.cart.CartScreen
import com.hayuwidyas.presentation.ui.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToProductDetail: (Int) -> Unit,
    onNavigateToAuth: () -> Unit
) {
    val navController = rememberNavController()
    var selectedTab by remember { mutableIntStateOf(0) }
    
    val bottomNavItems = listOf(
        Triple("Home", Icons.Default.Home, BottomNavDestination.HOME.route),
        Triple("Shop", Icons.Default.Store, BottomNavDestination.SHOP.route),
        Triple("Wishlist", Icons.Default.Favorite, BottomNavDestination.WISHLIST.route),
        Triple("Cart", Icons.Default.ShoppingCart, BottomNavDestination.CART.route),
        Triple("Profile", Icons.Default.Person, BottomNavDestination.PROFILE.route)
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, (label, icon, route) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavDestination.HOME.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavDestination.HOME.route) {
                HomeScreen(
                    onNavigateToProductDetail = onNavigateToProductDetail,
                    onNavigateToAuth = onNavigateToAuth
                )
            }
            composable(BottomNavDestination.SHOP.route) {
                ShopScreen(
                    onNavigateToProductDetail = onNavigateToProductDetail
                )
            }
            composable(BottomNavDestination.WISHLIST.route) {
                WishlistScreen(
                    onNavigateToProductDetail = onNavigateToProductDetail,
                    onNavigateToAuth = onNavigateToAuth
                )
            }
            composable(BottomNavDestination.CART.route) {
                CartScreen(
                    onNavigateToAuth = onNavigateToAuth
                )
            }
            composable(BottomNavDestination.PROFILE.route) {
                ProfileScreen(
                    onNavigateToAuth = onNavigateToAuth
                )
            }
        }
    }
}