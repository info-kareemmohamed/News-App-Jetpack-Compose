package com.example.news.details.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.R
import com.example.news.core.domain.model.Article
import com.example.news.core.domain.model.SourceType
import com.example.news.core.presentation.ui.theme.NewsTheme
import com.example.news.core.util.Dimens.ArticleImageHeight
import com.example.news.core.util.Dimens.ExtraSmallPadding_3
import com.example.news.core.util.Dimens.MediumPadding_24
import com.example.news.details.presentation.components.DetailsTopBar
import com.example.news.details.presentation.mvi.DetailsIntent
import com.example.news.details.presentation.mvi.DetailsState

@Composable
fun DetailsScreen(
    state: DetailsState,
    event: (DetailsIntent) -> Unit,
    navigateUp: () -> Unit,
) {


    DisposableEffect(Unit) {
        // Handle the final result of whether the item is bookmarked or not
        // and modify the bookmark state in the data layer when the composable is disposed.
        onDispose {
            event.invoke(DetailsIntent.SaveFinalBookmarkInData)
        }
    }

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            isBookmarked = state.isBookmarked,
            onBrowsingClick = {
                state.article?.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(url)
                    }
                    context.startActivity(intent)
                }
            },
            onShareClick = {
                state.article?.url?.let { shareUrl ->
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareUrl)
                    }
                    context.startActivity(Intent.createChooser(intent, "Share via"))
                }
            },
            onBookmarkClick = { event(DetailsIntent.OnBookmarkClick) },
            onBackClick = navigateUp
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MediumPadding_24)
        ) {
            item {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.article?.urlToImage)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(MediumPadding_24))
                Text(
                    text = state.article?.title ?: "",
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(
                        id = R.color.text_title
                    )
                )
                Spacer(modifier = Modifier.height(ExtraSmallPadding_3))
                Text(
                    text = state.article?.content ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(
                        id = R.color.body
                    )
                )


            }


        }


    }


}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    NewsTheme(dynamicColor = false) {
        DetailsScreen(
            state = DetailsState(
                article = Article(
                    author = "",
                    title = "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
                    description = "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
                    content = "We use cookies and data to Deliver and maintain Google services Track outages and protect against spam, fraud, and abuse Measure audience engagement and site statistics to unde… [+1131 chars]",
                    publishedAt = "2023-06-16T22:24:33Z",
                    sourceName = "BBS",
                    url = "https://consent.google.com/ml?continue=https://news.google.com/rss/articles/CBMiaWh0dHBzOi8vY3J5cHRvc2F1cnVzLnRlY2gvY29pbmJhc2Utc2F5cy1hcHBsZS1ibG9ja2VkLWl0cy1sYXN0LWFwcC1yZWxlYXNlLW9uLW5mdHMtaW4td2FsbGV0LXJldXRlcnMtY29tL9IBAA?oc%3D5&gl=FR&hl=en-US&cm=2&pc=n&src=1",
                    urlToImage = "https://media.wired.com/photos/6495d5e893ba5cd8bbdc95af/191:100/w_1280,c_limit/The-EU-Rules-Phone-Batteries-Must-Be-Replaceable-Gear-2BE6PRN.jpg",
                    isBookMarked = false,
                    sourceType = SourceType.HOME
                ),
            ),
            event = {},
            navigateUp = {}
        )
    }
}