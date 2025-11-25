package com.example.lecturetranscriber.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = RoyalBlue,
    onPrimary = Color.White,
    secondary = DeepViolet,
    tertiary = AccentRed,
    background = Color.White,
    surface = Color.White,
    onSurface = Color(0xFF1B1E28)
)

private val DarkColors = darkColorScheme(
    primary = RoyalBlue,
    onPrimary = Color.White,
    secondary = DeepViolet,
    tertiary = AccentRed
)

@Composable
fun LectureTranscriberTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = LectureTypography,
        shapes = LectureShapes,
        content = content
    )
}

