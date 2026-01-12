package com.example.news.presentation.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.designsystem.LocalSpacing
import com.example.designsystem.NewsTheme
import com.example.news.presentation.component.ArticleComponent
import com.example.news.presentation.component.ProviderHeaderComponent
import com.example.news.presentation.model.ArticleUi
import com.example.news.presentation.model.NewsProviderUi
import com.example.news.presentation.viewmodel.intent.NewsIntent
import com.example.news.presentation.viewmodel.state.NewsState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsSuccessLayout(
    state: NewsState,
    onIntent: (NewsIntent) -> Unit,
) {
    val spacing = LocalSpacing.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
    ) {
        state.providers.forEach { provider ->
            stickyHeader(key = provider.name) {
                ProviderHeaderComponent(name = provider.name)
            }
            items(
                items = provider.articles,
                key = { it.url + it.publishedAt }
            ) { article ->
                ArticleComponent(
                    item = article,
                    onOpenDetails = {
                        onIntent(
                            NewsIntent.OnArticleClick(article.url)
                        )
                    }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
    }
}

@PreviewLightDark
@Composable
private fun NewsSuccessLayoutPreview() {
    NewsTheme {
        NewsSuccessLayout(
            state = NewsState(
                providers = listOf(
                    NewsProviderUi(
                        name = "TechCrunch",
                        articles = listOf(
                            ArticleUi(
                                title = "AI Advances in 2024",
                                subtitle = "Latest breakthroughs in artificial intelligence",
                                provider = "TechCrunch",
                                publishedAt = "2 hours ago",
                                url = "https://example.com/article1",
                                imageUrl = "https://via.placeholder.com/400x300"
                            ),
                            ArticleUi(
                                title = "New Programming Languages",
                                subtitle = "Exploring emerging languages",
                                provider = "TechCrunch",
                                publishedAt = "4 hours ago",
                                url = "https://example.com/article2",
                                imageUrl = "https://via.placeholder.com/400x300"
                            )
                        )
                    ),
                    NewsProviderUi(
                        name = "The Verge",
                        articles = listOf(
                            ArticleUi(
                                title = "Latest Tech Gadgets",
                                subtitle = "The best devices of the season",
                                provider = "The Verge",
                                publishedAt = "1 hour ago",
                                url = "https://example.com/article3",
                                imageUrl = "https://via.placeholder.com/400x300"
                            )
                        )
                    )
                )
            ),
            onIntent = {}
        )
    }
}