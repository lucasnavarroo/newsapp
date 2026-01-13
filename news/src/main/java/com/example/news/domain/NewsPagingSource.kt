package com.example.news.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news.data.api.NewsApi
import com.example.news.data.model.ArticleResponse

class NewsPagingSource(
    private val api: NewsApi,
    private val country: String
) : PagingSource<Int, ArticleResponse>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ArticleResponse> {
        val page = params.key ?: 1

        return try {
            val response = api.getNews(
                country = country,
                page = page,
                pageSize = params.loadSize
            )

            val articles = response.articleResponses

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, ArticleResponse>
    ): Int? =
        state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.let { page ->
                page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
            }
        }
}


