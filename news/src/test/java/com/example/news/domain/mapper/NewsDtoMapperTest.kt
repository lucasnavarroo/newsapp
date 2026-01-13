package com.example.news.domain.mapper

import com.example.news.data.model.ArticleResponse
import com.example.news.data.model.NewsResponse
import org.junit.Test
import kotlin.test.assertEquals

class NewsDtoMapperTest {

    @Test
    fun `toDomain groups articles by provider and sorts providers by name`() {
        val dto = NewsResponse(
            status = "ok",
            totalResults = 3,
            articleResponses = listOf(
                article(provider = "B Provider", publishedAt = "2024-01-02T10:00:00Z", title = "b1"),
                article(provider = "A Provider", publishedAt = "2024-01-01T10:00:00Z", title = "a1"),
                article(provider = "B Provider", publishedAt = "2024-01-03T10:00:00Z", title = "b2"),
            )
        )

        val domain = dto.toDomain()

        assertEquals(listOf("A Provider", "B Provider"), domain.providers.map { it.sourceName })
        assertEquals(1, domain.providers.first { it.sourceName == "A Provider" }.articles.size)
        assertEquals(2, domain.providers.first { it.sourceName == "B Provider" }.articles.size)
    }

    @Test
    fun `toDomain sorts articles within each provider by publishedAt descending`() {
        val dto = NewsResponse(
            status = "ok",
            totalResults = 2,
            articleResponses = listOf(
                article(provider = "Provider", publishedAt = "2024-01-01T00:00:00Z", title = "older"),
                article(provider = "Provider", publishedAt = "2024-01-02T00:00:00Z", title = "newer"),
            )
        )

        val domain = dto.toDomain()
        val titles = domain.providers.single().articles.map { it.title }

        assertEquals(listOf("newer", "older"), titles)
    }

    private fun article(
        provider: String,
        publishedAt: String,
        title: String,
    ) = ArticleResponse(
        sourceResponse = ArticleResponse.SourceResponse(name = provider),
        author = null,
        title = title,
        description = null,
        imageUrl = null,
        publishedAt = publishedAt,
        url = "https://example.com/$title",
        content = null,
    )
}

