package com.example.news.data.repository

import android.util.Log
import com.example.news.data.api.NewsApi
import com.example.news.domain.News
import com.example.news.domain.NewsRepository
import com.example.news.domain.toDomain

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getHeadlines(): Result<News> = try {
        val response = api.getNews()
        Log.d("NEWS_API", "articles size = ${response.articleResponses.size}")
        Result.success(response.toDomain())
    } catch (t: Throwable) {
        Result.failure(t)
    }
}