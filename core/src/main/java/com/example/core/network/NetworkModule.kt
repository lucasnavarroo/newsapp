package com.example.core.network

import com.example.core.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newUrl = chain.request().url.newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
                    .build()

                chain.proceed(
                    chain.request().newBuilder().url(newUrl).build()
                )
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
