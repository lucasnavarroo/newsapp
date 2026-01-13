package com.example.news.presentation.viewmodel.intent

import com.example.news.presentation.model.ArticleUi

sealed interface NewsIntent {
    data class OnLoad(val isRefreshing: Boolean = false) : NewsIntent
    data class OnArticleClick(val article: ArticleUi) : NewsIntent
}