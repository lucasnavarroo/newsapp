package com.example.news.domain

interface NewsRepository {
    suspend fun getHeadlines(): Result<News>
}