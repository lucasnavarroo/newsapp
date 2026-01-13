package com.example.news.presentation.viewmodel.intent

import com.example.news.presentation.model.ArticleUi

sealed interface NewsIntent {
    object OnLoad : NewsIntent
    data class OnArticleClick(val article: ArticleUi) : NewsIntent
}