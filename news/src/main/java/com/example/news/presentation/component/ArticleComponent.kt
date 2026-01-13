package com.example.news.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.designsystem.LocalSpacing
import com.example.designsystem.NewsTheme
import com.example.news.R.drawable.error_loading
import com.example.news.presentation.model.ArticleUi

@Composable
fun ArticleComponent(
    item: ArticleUi,
    onOpenDetails: () -> Unit,
) {
    val spacing = LocalSpacing.current

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.spaceSmall)
            .clickable { onOpenDetails() }
    ) {
        Column {
            item.imageUrl?.let { imageUrl ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    error = painterResource(error_loading),
                    contentDescription = item.title,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }

            Column(
                modifier = Modifier.padding(spacing.twelve),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
            ) {

                Text(
                    text = item.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )

                item.subtitle?.let {
                    Text(
                        text = it,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Text(
                    text = item.author?.let { "$it - ${item.publishedAt}" } ?: item.publishedAt,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun ArticleComponentPreview() {
    NewsTheme {
        ArticleComponent(
            item = ArticleUi(
                title = "Breaking News: Major Event Unfolds in the City",
                subtitle = "Details are emerging about a significant event that has taken place in the downtown area.",
                imageUrl = "",
                publishedAt = "2 hours ago",
                url = "https://example.com/article",
                provider = "News Provider",
                content = "",
                author = "John Doe",
            ),
            onOpenDetails = {}
        )
    }
}
