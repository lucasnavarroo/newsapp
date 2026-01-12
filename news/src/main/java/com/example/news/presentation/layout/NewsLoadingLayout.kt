package com.example.news.presentation.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.designsystem.LocalSpacing
import com.example.designsystem.NewsTheme

@Composable
fun NewsLoadingLayout() {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

        Text(
            text = "Loading news...",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = spacing.spaceMedium),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@PreviewLightDark
@Composable
private fun NewsLoadingLayoutPreview() {
    NewsTheme {
        NewsLoadingLayout()
    }
}

