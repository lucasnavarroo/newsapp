package com.example.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.news.data.api.NewsApi
import com.example.news.domain.Article
import com.example.news.domain.News
import com.example.news.domain.NewsPagingSource
import com.example.news.domain.NewsRepository
import com.example.news.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {

    override fun getHeadlinesPaged(): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NewsPagingSource(api, country = "us")
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }

}