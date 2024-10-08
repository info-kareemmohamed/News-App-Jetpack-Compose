package com.example.news.core.presentation.common

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.news.core.util.Dimens.ExtraSmallPadding_6
import com.example.news.core.util.Dimens.MediumPadding_24
import com.example.news.core.domain.model.Article
import com.example.news.core.util.parseErrorMessage


@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding_24),
        contentPadding = PaddingValues(all = ExtraSmallPadding_6),
    ) {
        items(articles.size) { index ->
            articles[index].let { article ->
                ArticleCard(article = article, onClick = { onClick(article) })
            }
        }
    }

}


@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>?,
    onClick: (Article) -> Unit
) {
    articles?.let { articles ->
        val handlePagingResult = handlePagingResult(articles = articles)
        if (handlePagingResult) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(MediumPadding_24),
                contentPadding = PaddingValues(all = ExtraSmallPadding_6),
            ) {
                items(articles.itemCount) { index ->
                    articles[index]?.let { article ->
                        ArticleCard(article = article, onClick = { onClick(article) })
                    }
                }

            }
        }
    }

}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>,
): Boolean {
    val loadStatus = articles.loadState

    val error = when {
        loadStatus.refresh is LoadState.Error -> loadStatus.refresh as LoadState.Error
        loadStatus.prepend is LoadState.Error -> loadStatus.prepend as LoadState.Error
        loadStatus.append is LoadState.Error -> loadStatus.append as LoadState.Error
        else -> null
    }
    return when {
        loadStatus.refresh is LoadState.Loading -> {
            ArticlesListShimmerEffect()
            false
        }

        error != null -> {
            if (articles.itemCount > 0) {
                Toast.makeText(LocalContext.current, parseErrorMessage(error), Toast.LENGTH_SHORT)
                    .show()
                true
            } else {
                EmptyScreen(error = error)
                false
            }
        }

        else -> true
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesListShimmerEffect() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MediumPadding_24),
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.CenterHorizontally)
                .shimmerEffect()
        )


        repeat(10) {
            ArticleCardShimmerEffect()
        }
    }
}