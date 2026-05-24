package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CosmicColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = CosmicBlack,
    secondary = ElectricPurple,
    onSecondary = CosmicWhite,
    tertiary = RosePink,
    onTertiary = CosmicWhite,
    background = CosmicBlack,
    onBackground = CosmicWhite,
    surface = CosmicDeepNavy,
    onSurface = CosmicWhite,
    surfaceVariant = CosmicSlateCard,
    onSurfaceVariant = CosmicGreyText,
    error = RosePink,
    onError = CosmicWhite
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = CosmicColorScheme,
        typography = Typography,
        content = content
    )
}
