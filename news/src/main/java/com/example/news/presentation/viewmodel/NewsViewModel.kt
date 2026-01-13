package com.example.news.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    repository: NewsRepository
) : ViewModel() {

    val newsPagingFlow =
        repository.getHeadlinesPaged()
            .map { pagingData ->
                pagingData.map { it.toUi() }
            }
            .cachedIn(viewModelScope)

    private val _event = MutableSharedFlow<NewsEvent>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val event: SharedFlow<NewsEvent> = _event

    fun onIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.OnArticleClick ->
                emit(NewsEvent.OpenArticle(intent.url))
        }
    }

    private fun emit(event: NewsEvent) {
        _event.tryEmit(event)
    }
}

