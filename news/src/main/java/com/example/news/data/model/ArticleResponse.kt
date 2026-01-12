package com.example.news.data.model

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("source")
    val sourceResponse: SourceResponse,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("url")
    val url: String,

    @SerializedName("urlToImage")
    val imageUrl: String? = null,

    @SerializedName("publishedAt")
    val publishedAt: String,

    @SerializedName("content")
    val content: String? = null
) {
    data class SourceResponse(
        @SerializedName("id")
        val id: String? = null,

        @SerializedName("name")
        val name: String
    )
}