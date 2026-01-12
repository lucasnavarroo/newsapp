package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.core.network.networkModule
import com.example.designsystem.Dimensions
import com.example.designsystem.LocalSpacing
import com.example.designsystem.NewsTheme
import com.example.news.di.newsModule
import com.example.news.navigation.NewsRoute
import com.example.news.navigation.newsGraph
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@MainActivity)
                modules(
                    networkModule,
                    newsModule,
                )
            }
        }

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NewsTheme {
                CompositionLocalProvider(LocalSpacing provides Dimensions()) {
                    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        NavHost(
                            navController = navController,
                            startDestination = NewsRoute.ROOT,
                        ) {
                            newsGraph()
                        }
                    }
                }
            }
        }
    }
}