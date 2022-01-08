package com.mskang.mbti.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = ColorPrimary,
    primaryVariant = ColorPrimary,
    secondary = ColorSecondary,
    background = Gray50,
    onPrimary = Color.White
)

private val LightColorPalette = lightColors(
    primary = ColorPrimary,
    primaryVariant = ColorPrimary,
    secondary = ColorSecondary,
    background = Gray50,
    onPrimary = Color.White
)

@Composable
fun MainTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}