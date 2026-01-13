package com.example.news.presentation.mapper

import com.example.core.presentation.toUiDateString
import com.example.news.domain.model.Article
import com.example.news.domain.model.News
import com.example.news.presentation.model.ArticleUi
import com.example.news.presentation.model.NewsProviderUi

fun News.toUi(): List<NewsProviderUi> =
    providers.map { provider ->
        NewsProviderUi(
            name = provider.sourceName,
            articles = provider.articles.map { it.toUi() }
        )
    }

fun Article.toUi(): ArticleUi =
    ArticleUi(
        title = title,
        subtitle = description,
        imageUrl = imageUrl,
        publishedAt = publishedAt.toUiDateString(pattern = "d MMM yyyy 'at' h:mm a"),
        provider = provider,
        url = url,
        content = content,
        author = author,
    )
