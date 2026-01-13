package com.example.news.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getHeadlinesPaged(): Flow<PagingData<Article>>
}