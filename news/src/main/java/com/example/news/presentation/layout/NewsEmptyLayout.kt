package com.example.news.presentation.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.designsystem.LocalSpacing
import com.example.designsystem.NewsTheme

@Composable
fun NewsEmptyLayout() {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No News Available",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "There are no news articles to display at the moment. Please check back later.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = spacing.spaceSmall),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@PreviewLightDark
@Composable
private fun NewsEmptyLayoutPreview() {
    NewsTheme {
        NewsEmptyLayout()
    }
}