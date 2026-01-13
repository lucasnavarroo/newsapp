package com.example.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.news.navigation.NewsNavArgs.ARTICLE
import com.example.news.presentation.NewsDetailScreen
import com.example.news.presentation.NewsScreen
import com.example.news.presentation.model.ArticleUi

object NewsRoute {
    const val ROOT = "news"
    const val DETAILS = "news_details"
}

object NewsNavArgs {
    const val ARTICLE = "article"
}

fun NavController.navigateToNewsDetails(article: ArticleUi) {
    currentBackStackEntry
        ?.savedStateHandle
        ?.set(
            ARTICLE,
            article
        )

    navigate(NewsRoute.DETAILS)
}

fun NavGraphBuilder.newsGraph(navController: NavController) {
    composable(NewsRoute.ROOT) {
        NewsScreen(navController)
    }

    composable(NewsRoute.DETAILS) {
        val article =
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<ArticleUi>(ARTICLE)

        article?.let { it ->
            NewsDetailScreen(
                article = it,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
