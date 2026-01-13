package com.example.news.presentation.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class NewsProviderUi(
    val name: String,
    val articles: List<ArticleUi>
)

@Parcelize
data class ArticleUi(
    val title: String,
    val subtitle: String?,
    val imageUrl: String?,
    val publishedAt: String,
    val url: String,
    val provider: String,
    val content: String?,
    val author: String?,
) : Parcelable