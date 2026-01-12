package com.example.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun NewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    spacing: Dimensions = Dimensions(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkScheme else LightScheme
    CompositionLocalProvider(LocalSpacing provides spacing) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = content
        )
    }
}
