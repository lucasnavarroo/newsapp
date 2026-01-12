package com.example.news.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.designsystem.LocalSpacing

@Composable
fun ProviderHeaderComponent(
    name: String
) {
    val spacing = LocalSpacing.current

    Text(
        text = name,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = spacing.spaceSmall,
                bottom = spacing.spaceSmall,
                start = spacing.spaceMedium,
                end = spacing.spaceMedium,
            )
    )
}