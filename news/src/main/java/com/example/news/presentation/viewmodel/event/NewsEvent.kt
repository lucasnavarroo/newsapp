package com.example.news.presentation.viewmodel.event

sealed interface NewsEvent {
    data class OpenArticle(val url: String) : NewsEvent
}