package com.example.news.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.NewsRepository
import com.example.news.presentation.mapper.toUi
import com.example.news.presentation.viewmodel.event.NewsEvent
import com.example.news.presentation.viewmodel.intent.NewsIntent
import com.example.news.presentation.viewmodel.state.NewsState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<NewsEvent>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val event: SharedFlow<NewsEvent> = _event

    fun onIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.OnLoad ->
                loadNews(isRefreshing = intent.isRefreshing)

            is NewsIntent.OnArticleClick ->
                emit(NewsEvent.OpenArticle(intent.url))
        }
    }

    private fun loadNews(isRefreshing: Boolean) {
        viewModelScope.launch {

            if (isRefreshing) {
                _state.update { it.copy(isRefreshing = true) }
            } else {
                _state.update {
                    it.copy(
                        screenType = NewsState.ScreenType.Loading,
                        isRefreshing = false
                    )
                }
            }

            repository.getHeadlines()
                .onSuccess { news ->
                    val providersUi = news.toUi()

                    _state.update {
                        it.copy(
                            screenType =
                                if (providersUi.isEmpty())
                                    NewsState.ScreenType.Empty
                                else
                                    NewsState.ScreenType.Success,
                            providers = providersUi,
                            isRefreshing = false
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            screenType = NewsState.ScreenType.Error,
                            isRefreshing = false
                        )
                    }
                }
        }
    }


    private fun emit(event: NewsEvent) {
        _event.tryEmit(event)
    }
}
