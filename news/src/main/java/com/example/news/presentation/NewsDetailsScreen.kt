package com.example.news.presentation

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.designsystem.LocalSpacing
import com.example.designsystem.NewsTheme
import com.example.designsystem.component.ImagePlaceholder
import com.example.news.R
import com.example.news.presentation.model.ArticleUi
import com.example.designsystem.R as DsR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    article: ArticleUi,
    onBack: () -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.details_top_bar_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.details_back_content_description
                            )
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface {
                Button(
                    onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                article.url.toUri()
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.spaceMedium)
                ) {
                    Text(stringResource(R.string.details_button_open_article))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            val imageModifier = Modifier
                .fillMaxWidth()
                .height(220.dp)

            if (article.imageUrl.isNullOrBlank()) {
                ImagePlaceholder(modifier = imageModifier)
            } else {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = article.title,
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(DsR.drawable.ic_image_placeholder),
                    error = painterResource(DsR.drawable.ic_image_placeholder)
                )
            }

            Spacer(Modifier.height(spacing.spaceMedium))

            Column(
                modifier = Modifier.padding(horizontal = spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                article.subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                article.content?.let {
                    Spacer(Modifier.height(spacing.spaceSmall))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun NewsDetailScreenPreview_WithImage() {
    NewsTheme {
        NewsDetailScreen(
            article = ArticleUi(
                title = "Jetpack Compose: What’s new in 2026",
                subtitle = "A quick tour of the latest APIs and best practices",
                imageUrl = "https://via.placeholder.com/800x500",
                publishedAt = "Jan 13, 2026",
                url = "https://example.com/articles/compose-2026",
                provider = "TechCrunch",
                content = "This is a sample article content used only for preview purposes.",
                author = "Jane Smith",
            ),
            onBack = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun NewsDetailScreenPreview_NoImage() {
    NewsTheme {
        NewsDetailScreen(
            article = ArticleUi(
                title = "Local news without an image",
                subtitle = "This preview exercises the ImagePlaceholder branch",
                imageUrl = "",
                publishedAt = "Jan 13, 2026",
                url = "https://example.com/articles/no-image",
                provider = "The Verge",
                content = "Another sample content string.",
                author = null,
            ),
            onBack = {}
        )
    }
}
