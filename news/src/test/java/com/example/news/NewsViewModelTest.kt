package com.example.news

import app.cash.turbine.test
import com.example.news.domain.model.Article
import com.example.news.domain.model.News
import com.example.news.domain.model.NewsProvider
import com.example.news.domain.repository.NewsRepository
import com.example.news.presentation.model.ArticleUi
import com.example.news.presentation.viewmodel.NewsViewModel
import com.example.news.presentation.viewmodel.event.NewsEvent
import com.example.news.presentation.viewmodel.intent.NewsIntent
import com.example.news.presentation.viewmodel.state.NewsState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: NewsRepository

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `loadNews success updates state to Success`() = runTest(dispatcher) {
        val articles = listOf(
            Article(
                provider = "Provider",
                author = "Author 1",
                title = "Title 1",
                description = "Description 1",
                imageUrl = "https://example.com/image1.jpg",
                publishedAt = java.time.Instant.now(),
                url = "https://example.com/article1",
                content = "Content 1"
            ),
            Article(
                provider = "Provider",
                author = "Author 2",
                title = "Title 2",
                description = "Description 2",
                imageUrl = "https://example.com/image2.jpg",
                publishedAt = java.time.Instant.now(),
                url = "https://example.com/article2",
                content = "Content 2"
            )
        )
        val news = News(providers = listOf(NewsProvider("Provider", articles)))
        coEvery { repository.getHeadlines() } returns Result.success(news)

        val viewModel = NewsViewModel(repository)

        viewModel.state.test {
            val initial = awaitItem()
            assertEquals(NewsState.ScreenType.Loading, initial.screenType)

            advanceUntilIdle()
            val afterLoad = awaitItem()
            assertEquals(NewsState.ScreenType.Success, afterLoad.screenType)
            assertTrue(afterLoad.providers.isNotEmpty())
            assertTrue(afterLoad.providers.first().articles.isNotEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadNews empty updates state to Empty`() = runTest(dispatcher) {
        val news = News(providers = emptyList())
        coEvery { repository.getHeadlines() } returns Result.success(news)

        val viewModel = NewsViewModel(repository)

        viewModel.state.test {
            val initial = awaitItem()
            assertEquals(NewsState.ScreenType.Loading, initial.screenType)

            advanceUntilIdle()
            val afterLoad = awaitItem()
            assertEquals(NewsState.ScreenType.Empty, afterLoad.screenType)
            assertTrue(afterLoad.providers.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadNews error updates state to Error`() = runTest(dispatcher) {
        coEvery { repository.getHeadlines() } returns Result.failure(Exception("error"))

        val viewModel = NewsViewModel(repository)

        viewModel.state.test {
            val initial = awaitItem()
            assertEquals(NewsState.ScreenType.Loading, initial.screenType)

            advanceUntilIdle()
            val afterLoad = awaitItem()
            assertEquals(NewsState.ScreenType.Error, afterLoad.screenType)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onArticleClick emits OpenArticle event`() = runTest(dispatcher) {
        coEvery { repository.getHeadlines() } returns Result.success(News(providers = emptyList()))
        val article = ArticleUi(
            title = "Title",
            subtitle = "Subtitle",
            imageUrl = "imageUrl",
            publishedAt = "date",
            url = "url",
            provider = "Provider",
            content = "Content",
            author = "Author"
        )
        val viewModel = NewsViewModel(repository)

        viewModel.event.test {
            viewModel.onIntent(NewsIntent.OnArticleClick(article))
            val event = awaitItem()
            assertTrue(event is NewsEvent.OpenArticle)
            assertEquals(article, event.article)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
