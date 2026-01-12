package com.example.designsystem

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val default: Dp = 0.dp,
    val one: Dp = 1.dp,
    val two: Dp = 2.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val twelve: Dp = 12.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 64.dp,
    val eighteen: Dp = 18.dp
)

val LocalSpacing = compositionLocalOf { Dimensions() }