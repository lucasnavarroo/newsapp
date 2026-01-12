package com.example.news.presentation.viewmodel.intent

sealed interface NewsIntent {
    data class OnLoad(val isRefreshing: Boolean = false) : NewsIntent
    data class OnArticleClick(val url: String) : NewsIntent
}