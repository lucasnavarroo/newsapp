package com.example.news

import com.example.core.presentation.toUiDateString
import com.example.news.domain.model.Article
import com.example.news.domain.model.News
import com.example.news.domain.model.NewsProvider
import com.example.news.presentation.mapper.toUi
import org.junit.BeforeClass
import org.junit.Test
import java.time.Instant
import java.util.Locale
import java.util.TimeZone
import kotlin.test.assertEquals

class NewsUiMapperTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun setDefaults() {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
            Locale.setDefault(Locale.US)
        }
    }

    @Test
    fun `News toUi maps providers and articles`() {
        val a1 = Article(
            provider = "Provider B",
            author = "Alice",
            title = "T1",
            description = "D1",
            imageUrl = "https://img/1",
            publishedAt = Instant.parse("2024-01-01T12:00:00Z"),
            url = "https://u/1",
            content = "C1",
        )
        val a2 = Article(
            provider = "Provider B",
            author = null,
            title = "T2",
            description = null,
            imageUrl = null,
            publishedAt = Instant.parse("2024-01-02T12:00:00Z"),
            url = "https://u/2",
            content = null,
        )

        val news = News(
            providers = listOf(
                NewsProvider(sourceName = "Provider B", articles = listOf(a1, a2))
            )
        )

        val ui = news.toUi()

        assertEquals(1, ui.size)
        assertEquals("Provider B", ui[0].name)
        assertEquals(2, ui[0].articles.size)

        val uiA1 = ui[0].articles[0]
        assertEquals("T1", uiA1.title)
        assertEquals("D1", uiA1.subtitle)
        assertEquals("https://img/1", uiA1.imageUrl)
        assertEquals("https://u/1", uiA1.url)
        assertEquals("Provider B", uiA1.provider)
        assertEquals("C1", uiA1.content)
        assertEquals("Alice", uiA1.author)
    }

    @Test
    fun `Article toUi formats publishedAt with expected pattern deterministically`() {
        val pattern = "d MMM yyyy 'at' h:mm a"
        val instant = Instant.parse("2024-01-01T00:05:00Z")

        val article = Article(
            provider = "Provider A",
            author = "Bob",
            title = "Title",
            description = "Desc",
            imageUrl = null,
            publishedAt = instant,
            url = "https://example.com",
            content = null,
        )

        val ui = article.toUi()

        val expected = instant.toUiDateString(pattern = pattern)

        assertEquals(expected, ui.publishedAt)
        assertEquals(true, ui.publishedAt.isNotBlank())
    }
}