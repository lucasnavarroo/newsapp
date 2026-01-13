package com.example.newsapp

import com.example.core.config.AppConfig
import org.koin.dsl.module

val configModule = module {
    single {
        AppConfig(
            newsCountry = BuildConfig.NEWS_COUNTRY
        )
    }
}