package com.example.news.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.R
import com.example.news.presentation.layout.NewsEmptyLayout
import com.example.news.presentation.layout.NewsErrorLayout
import com.example.news.presentation.layout.NewsLoadingLayout
import com.example.news.presentation.layout.NewsSuccessLayout
import com.example.news.presentation.viewmodel.NewsViewModel
import com.example.news.presentation.viewmodel.event.NewsEvent
import com.example.news.presentation.viewmodel.intent.NewsIntent
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = koinViewModel()
) {
    val articles = viewModel.newsPagingFlow
        .collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is NewsEvent.OpenArticle -> {
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.news_top_bar_title))
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

            when (articles.loadState.refresh) {
                is LoadState.Loading -> {
                    NewsLoadingLayout()
                }

                is LoadState.Error -> {
                    NewsErrorLayout(
                        onRetry = { articles.retry() }
                    )
                }

                is LoadState.NotLoading -> {
                    if (articles.itemCount == 0) {
                        NewsEmptyLayout()
                    } else {
                        NewsSuccessLayout(
                            state = articles,
                            onArticleClick = { url ->
                                viewModel.onIntent(
                                    NewsIntent.OnArticleClick(url)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
