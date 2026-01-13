package com.example.news.presentation.viewmodel.state

import com.example.news.presentation.model.NewsProviderUi

data class NewsState(
    val screenType: ScreenType = ScreenType.Loading,
    val providers: List<NewsProviderUi> = emptyList()
) {
    enum class ScreenType {
        Loading, Error, Success, Empty
    }
}
