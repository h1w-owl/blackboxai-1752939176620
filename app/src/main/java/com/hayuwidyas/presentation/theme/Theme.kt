package com.hayuwidyas.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Hayu Widyas Brand Colors
 * 
 * Luxury minimalist color palette inspired by the brand identity
 * Primary: Pink #ED1B76, Secondary: Black, White, Soft Gray
 */
object HayuWidyasColors {
    val PrimaryPink = Color(0xFFED1B76)
    val PrimaryPinkDark = Color(0xFFC91660)
    val PrimaryPinkLight = Color(0xFFF04D92)
    
    val Black = Color(0xFF000000)
    val White = Color(0xFFFFFFFF)
    val SoftGray = Color(0xFFF5F5F5)
    val MediumGray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    
    val BackgroundPrimary = Color(0xFFFFFFFF)
    val BackgroundSecondary = Color(0xFFF8F8F8)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFF5F5F5)
    
    val TextPrimary = Color(0xFF000000)
    val TextSecondary = Color(0xFF666666)
    val TextHint = Color(0xFF9E9E9E)
    val TextOnPrimary = Color(0xFFFFFFFF)
    
    val Success = Color(0xFF4CAF50)
    val Error = Color(0xFFF44336)
    val Warning = Color(0xFFFF9800)
    val Info = Color(0xFF2196F3)
    
    val OverlayDark = Color(0x80000000)
    val OverlayLight = Color(0x80FFFFFF)
    
    val ShimmerBase = Color(0xFFF0F0F0)
    val ShimmerHighlight = Color(0xFFFFFFFF)
}

/**
 * Light color scheme for Hayu Widyas
 */
private val LightColorScheme = lightColorScheme(
    primary = HayuWidyasColors.PrimaryPink,
    onPrimary = HayuWidyasColors.TextOnPrimary,
    primaryContainer = HayuWidyasColors.PrimaryPinkLight,
    onPrimaryContainer = HayuWidyasColors.TextPrimary,
    
    secondary = HayuWidyasColors.Black,
    onSecondary = HayuWidyasColors.White,
    secondaryContainer = HayuWidyasColors.DarkGray,
    onSecondaryContainer = HayuWidyasColors.White,
    
    tertiary = HayuWidyasColors.MediumGray,
    onTertiary = HayuWidyasColors.White,
    tertiaryContainer = HayuWidyasColors.SoftGray,
    onTertiaryContainer = HayuWidyasColors.TextPrimary,
    
    error = HayuWidyasColors.Error,
    onError = HayuWidyasColors.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    
    background = HayuWidyasColors.BackgroundPrimary,
    onBackground = HayuWidyasColors.TextPrimary,
    
    surface = HayuWidyasColors.Surface,
    onSurface = HayuWidyasColors.TextPrimary,
    surfaceVariant = HayuWidyasColors.SurfaceVariant,
    onSurfaceVariant = HayuWidyasColors.TextSecondary,
    
    outline = HayuWidyasColors.MediumGray,
    outlineVariant = HayuWidyasColors.SoftGray,
    
    scrim = HayuWidyasColors.OverlayDark,
    
    inverseSurface = HayuWidyasColors.Black,
    inverseOnSurface = HayuWidyasColors.White,
    inversePrimary = HayuWidyasColors.PrimaryPinkLight,
    
    surfaceDim = Color(0xFFDDD7D7),
    surfaceBright = HayuWidyasColors.White,
    surfaceContainerLowest = HayuWidyasColors.White,
    surfaceContainerLow = Color(0xFFF7F2F2),
    surfaceContainer = Color(0xFFF1ECEC),
    surfaceContainerHigh = Color(0xFFEBE6E6),
    surfaceContainerHighest = Color(0xFFE6E1E1)
)

/**
 * Dark color scheme for Hayu Widyas (optional)
 */
private val DarkColorScheme = darkColorScheme(
    primary = HayuWidyasColors.PrimaryPinkLight,
    onPrimary = HayuWidyasColors.Black,
    primaryContainer = HayuWidyasColors.PrimaryPinkDark,
    onPrimaryContainer = HayuWidyasColors.White,
    
    secondary = HayuWidyasColors.White,
    onSecondary = HayuWidyasColors.Black,
    secondaryContainer = HayuWidyasColors.DarkGray,
    onSecondaryContainer = HayuWidyasColors.White,
    
    tertiary = HayuWidyasColors.SoftGray,
    onTertiary = HayuWidyasColors.Black,
    tertiaryContainer = HayuWidyasColors.MediumGray,
    onTertiaryContainer = HayuWidyasColors.White,
    
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    
    background = Color(0xFF121212),
    onBackground = HayuWidyasColors.White,
    
    surface = Color(0xFF121212),
    onSurface = HayuWidyasColors.White,
    surfaceVariant = Color(0xFF1E1E1E),
    onSurfaceVariant = HayuWidyasColors.SoftGray,
    
    outline = HayuWidyasColors.MediumGray,
    outlineVariant = HayuWidyasColors.DarkGray,
    
    scrim = HayuWidyasColors.OverlayDark,
    
    inverseSurface = HayuWidyasColors.White,
    inverseOnSurface = HayuWidyasColors.Black,
    inversePrimary = HayuWidyasColors.PrimaryPink,
    
    surfaceDim = Color(0xFF121212),
    surfaceBright = Color(0xFF383838),
    surfaceContainerLowest = Color(0xFF0D0D0D),
    surfaceContainerLow = Color(0xFF1A1A1A),
    surfaceContainer = Color(0xFF1E1E1E),
    surfaceContainerHigh = Color(0xFF292929),
    surfaceContainerHighest = Color(0xFF333333)
)

/**
 * Hayu Widyas Theme Composable
 * 
 * Applies the luxury brand theme with proper color schemes and typography.
 * Currently uses light theme only to match the brand's clean aesthetic.
 */
@Composable
fun HayuWidyasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // For now, always use light theme to match brand identity
        // darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HayuWidyasTypography,
        shapes = HayuWidyasShapes,
        content = content
    )
}

/**
 * Preview theme for Compose previews
 */
@Composable
fun HayuWidyasThemePreview(
    content: @Composable () -> Unit
) {
    HayuWidyasTheme(darkTheme = false) {
        content()
    }
}
