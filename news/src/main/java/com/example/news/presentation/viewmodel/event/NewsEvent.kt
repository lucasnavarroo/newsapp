package com.example.news.presentation.viewmodel.event

import com.example.news.presentation.model.ArticleUi

sealed interface NewsEvent {
    data class OpenArticle(val article: ArticleUi) : NewsEvent
}