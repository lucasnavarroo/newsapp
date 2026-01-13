package com.example.news.domain.mapper

import com.example.core.presentation.toInstantOrNull
import com.example.news.data.model.ArticleResponse
import com.example.news.data.model.NewsResponse
import com.example.news.domain.model.Article
import com.example.news.domain.model.News
import com.example.news.domain.model.NewsProvider
import java.time.Instant
fun NewsResponse.toDomain(): News {
    val articles = articleResponses.map { it.toDomain() }

    val providers = articles
        .groupBy { it.provider }
        .map { (providerName, providerArticles) ->
            NewsProvider(
                sourceName = providerName,
                articles = providerArticles
                    .sortedByDescending { it.publishedAt }
            )
        }
        .sortedBy { it.sourceName }

    return News(providers = providers)
}


fun ArticleResponse.toDomain(): Article =
    Article(
        provider = sourceResponse.name,
        author = author,
        title = title,
        description = description,
        imageUrl = imageUrl,
        publishedAt = publishedAt.toInstantOrNull() ?: Instant.MIN,
        url = url,
        content = content,
    )
