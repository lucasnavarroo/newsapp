package com.example.news.di

import com.example.news.domain.NewsRepository
import com.example.news.data.repository.NewsRepositoryImpl
import com.example.news.data.api.NewsApi
import com.example.news.presentation.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val newsModule = module {

    single<NewsApi> {
        get<Retrofit>().create(NewsApi::class.java)
    }

    single<NewsRepository> {
        NewsRepositoryImpl(get())
    }

    viewModel {
        NewsViewModel(get())
    }
}

