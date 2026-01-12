package com.example.news.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.news.presentation.NewsScreen

object NewsRoute { const val ROOT = "news" }

fun NavGraphBuilder.newsGraph() {
    composable(NewsRoute.ROOT) { NewsScreen() }
}