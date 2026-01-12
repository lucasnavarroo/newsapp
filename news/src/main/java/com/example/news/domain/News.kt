package com.example.news.domain

import java.time.Instant

data class News(
    val providers: List<NewsProvider>
)

data class NewsProvider(
    val sourceName: String,
    val articles: List<Article>
)

data class Article(
    val provider: String,
    val author: String?,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val publishedAt: Instant,
    val url: String
)