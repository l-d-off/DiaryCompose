package ru.darf.diarycompose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorScheme(
    val textPrimary: Color,
    val textSecondary: Color,
    val textField: Color,
    val backgroundPrimary: Color,
    val backgroundSecondary: Color,
    val backgroundTertiary: Color,
    val topIconBackground: Color,
    val topIconTint: Color,
    val sky: Color
)

val LocalColor = staticCompositionLocalOf { ColorSchemeImpl }

object AppTheme {
    val colors: ColorScheme
        @Composable
        get() = LocalColor.current
}