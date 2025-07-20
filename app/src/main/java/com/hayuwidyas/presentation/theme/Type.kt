package com.hayuwidyas.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Hayu Widyas Typography
 * 
 * Elegant typography system for the luxury brand.
 * Uses system fonts with proper hierarchy and spacing.
 */

// Font families for Hayu Widyas
val HayuWidyasFontFamily = FontFamily.Default // Using system default for now

/**
 * Custom Typography for Hayu Widyas
 * 
 * Follows Material Design 3 typography scale with luxury brand adjustments.
 * Emphasizes elegance and readability.
 */
val HayuWidyasTypography = Typography(
    // Display styles - for large, impactful text
    displayLarge = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        color = HayuWidyasColors.TextPrimary
    ),
    displayMedium = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    displaySmall = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    
    // Headline styles - for section headers
    headlineLarge = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    headlineMedium = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    headlineSmall = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    
    // Title styles - for card titles and important text
    titleLarge = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    titleMedium = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    titleSmall = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    
    // Body styles - for main content
    bodyLarge = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    bodyMedium = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    bodySmall = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = HayuWidyasColors.TextSecondary
    ),
    
    // Label styles - for buttons and small text
    labelLarge = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    labelMedium = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = HayuWidyasColors.TextPrimary
    ),
    labelSmall = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = HayuWidyasColors.TextSecondary
    )
)

/**
 * Custom text styles for specific use cases
 */
object HayuWidyasTextStyles {
    
    // Brand name style
    val BrandName = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = HayuWidyasColors.TextPrimary
    )
    
    // Product price style
    val ProductPrice = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = HayuWidyasColors.PrimaryPink
    )
    
    // Product price crossed out (original price)
    val ProductPriceCrossed = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        color = HayuWidyasColors.TextSecondary
    )
    
    // Button text style
    val ButtonText = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = HayuWidyasColors.TextOnPrimary
    )
    
    // Navigation label style
    val NavigationLabel = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = HayuWidyasColors.TextSecondary
    )
    
    // Category tag style
    val CategoryTag = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp,
        color = HayuWidyasColors.TextSecondary
    )
    
    // Error message style
    val ErrorMessage = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = HayuWidyasColors.Error
    )
    
    // Success message style
    val SuccessMessage = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = HayuWidyasColors.Success
    )
    
    // Tagline style
    val Tagline = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = HayuWidyasColors.TextSecondary,
        fontStyle = FontStyle.Italic
    )
    
    // Product description style
    val ProductDescription = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp,
        color = HayuWidyasColors.TextPrimary
    )
    
    // Rating text style
    val RatingText = TextStyle(
        fontFamily = HayuWidyasFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = HayuWidyasColors.TextSecondary
    )
}
