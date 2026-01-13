package com.example.news.data.repository

import com.example.core.config.AppConfig
import com.example.news.data.api.NewsApi
import com.example.news.domain.model.News
import com.example.news.domain.repository.NewsRepository
import com.example.news.domain.mapper.toDomain

class NewsRepositoryImpl(
    private val api: NewsApi,
    private val config: AppConfig
) : NewsRepository {

    override suspend fun getHeadlines(): Result<News> = runCatching {
        api.getNews(
            country = config.newsCountry
        ).toDomain()
    }
}