package com.example.designsystem

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val PrimaryLight = Color(0xFF5D6B7B)
val PrimaryDark = Color(0xFFB8C5D6)

val PrimaryContainerLight = Color(0xFFE1E7F0)
val PrimaryContainerDark = Color(0xFF434D5A)

val SecondaryLight = Color(0xFF6B7680)
val SecondaryDark = Color(0xFFC2CDD8)

val SecondaryContainerLight = Color(0xFFEEF1F7)
val SecondaryContainerDark = Color(0xFF505B66)

val TertiaryLight = Color(0xFF7B6B7D)
val TertiaryDark = Color(0xFFD4C5D7)

val ErrorLight = Color(0xFFB3261E)
val ErrorDark = Color(0xFFF9DEDC)

val LightScheme: ColorScheme = lightColorScheme(
    primary = PrimaryLight,
    primaryContainer = PrimaryContainerLight,
    secondary = SecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    tertiary = TertiaryLight,
    error = ErrorLight
)

val DarkScheme: ColorScheme = darkColorScheme(
    primary = PrimaryDark,
    primaryContainer = PrimaryContainerDark,
    secondary = SecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    tertiary = TertiaryDark,
    error = ErrorDark
)