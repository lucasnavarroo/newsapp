package com.example.news.presentation.model

data class NewsProviderUi(
    val name: String,
    val articles: List<ArticleUi>
)

data class ArticleUi(
    val title: String,
    val subtitle: String?,
    val imageUrl: String?,
    val publishedAt: String,
    val url: String,
    val provider: String
)