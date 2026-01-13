package com.example.news.presentation.mapper

import com.example.news.domain.Article
import com.example.news.domain.News
import com.example.news.presentation.model.ArticleUi
import com.example.news.presentation.model.NewsProviderUi
import com.example.news.toUiDateString

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
        publishedAt = publishedAt.toUiDateString(),
        provider = provider,
        url = url,
        content = content,
        author = author,
    )
