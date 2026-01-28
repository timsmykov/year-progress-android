package com.yearprogress.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val PurpleGrey20 = Color(0xFF402B69)
val Pink20 = Color(0xFF6D1B9B)

// Dark theme colors (for Pixel 8 default)
private val DarkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = Purple80,
    onPrimary = Color.White,
    primaryContainer = PurpleGrey80,
    onPrimaryContainer = PurpleGrey20,
    secondary = Pink80,
    onSecondary = Color.White,
    secondaryContainer = Pink80,
    onSecondaryContainer = Pink20,
    tertiary = Purple80,
    onTertiary = Color.White,
    tertiaryContainer = PurpleGrey80,
    onTertiaryContainer = PurpleGrey20,
    error = Color.Red.copy(alpha = 0.8f),
    onError = Color.White,
    errorContainer = Color.Red.copy(alpha = 0.1f),
    onErrorContainer = Color.Red.copy(alpha = 0.2f),
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.DarkGray.copy(alpha = 0.9f),
    onSurface = Color.White,
    surfaceVariant = Color.DarkGray.copy(alpha = 0.7f),
    onSurfaceVariant = Color.White,
    outline = Color.Gray.copy(alpha = 0.5f),
    outlineVariant = Color.Gray.copy(alpha = 0.3f),
    scrim = Color.Black.copy(alpha = 0.5f),
    inverseSurface = Color.White,
    inverseOnSurface = Color.Black,
    inversePrimary = PurpleGrey20,
    inverseOnPrimary = PurpleGrey80,
)

@Composable
fun Theme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
