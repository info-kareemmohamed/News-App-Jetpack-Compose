package com.example.news.core.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.news.core.util.Dimens.ExtraSmallPadding_6
import com.example.news.core.util.Dimens.MediumPadding_24
import com.example.news.core.domain.model.Article

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles = articles)
    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding_24),
            contentPadding = PaddingValues(all = ExtraSmallPadding_6),
        ) {
          items(articles.itemCount){index->
              articles[index]?.let { article ->
                  ArticleCard(article = article, onClick = {onClick(article)})
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
            EmptyScreen(error = error)
            false
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
        repeat(10) {
            ArticleCardShimmerEffect()
        }
    }
}