package com.example.news

import com.example.core.config.AppConfig
import com.example.news.data.api.NewsApi
import com.example.news.data.model.ArticleResponse
import com.example.news.data.model.NewsResponse
import com.example.news.data.repository.NewsRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryImplTest {
    private val api: NewsApi = mockk(relaxed = false)
    private val config: AppConfig = mockk(relaxed = true)
    private val repo = NewsRepositoryImpl(api, config)

    @Test
    fun `getHeadlines success maps dto to domain`() = runTest {
        coEvery { config.newsCountry } returns "us"

        val dto = NewsResponse(
            status = "ok",
            totalResults = 1,
            articleResponses = listOf(
                ArticleResponse(
                    sourceResponse = ArticleResponse.SourceResponse(name = "Provider A"),
                    author = "Author",
                    title = "Title",
                    description = "Desc",
                    imageUrl = "https://example.com/img.png",
                    publishedAt = "2024-01-01T00:00:00Z",
                    url = "https://example.com/article",
                    content = "Content"
                )
            )
        )

        coEvery { api.getNews(country = "us") } returns dto

        val result = repo.getHeadlines()

        assertTrue(result.isSuccess)
        val data = result.getOrNull()
        assertNotNull(data)

        assertEquals(1, data.providers.size)
        assertEquals("Provider A", data.providers[0].sourceName)
        assertEquals(1, data.providers[0].articles.size)

        val article = data.providers[0].articles[0]
        assertEquals("Provider A", article.provider)
        assertEquals("Author", article.author)
        assertEquals("Title", article.title)
        assertEquals("Desc", article.description)
        assertEquals("https://example.com/img.png", article.imageUrl)
        assertEquals(Instant.parse("2024-01-01T00:00:00Z"), article.publishedAt)
        assertEquals("https://example.com/article", article.url)
        assertEquals("Content", article.content)
    }

    @Test
    fun `getHeadlines http error returns HttpException`() = runTest {
        coEvery { config.newsCountry } returns "us"
        coEvery { api.getNews(country = "us") } throws httpException(404)

        val result = repo.getHeadlines()

        assertTrue(result.isFailure)
        val ex = result.exceptionOrNull()
        assertTrue(ex is HttpException)
        assertEquals(404, ex.code())
    }

    @Test
    fun `getHeadlines network error returns IOException`() = runTest {
        coEvery { config.newsCountry } returns "us"
        coEvery { api.getNews(country = "us") } throws IOException("offline")

        val result = repo.getHeadlines()

        assertTrue(result.isFailure)
        val ex = result.exceptionOrNull()
        assertTrue(ex is IOException)
    }

    private fun httpException(code: Int, msg: String = ""): HttpException {
        val body = msg.toResponseBody("application/json".toMediaType())
        return HttpException(Response.error<Any>(code, body))
    }
}