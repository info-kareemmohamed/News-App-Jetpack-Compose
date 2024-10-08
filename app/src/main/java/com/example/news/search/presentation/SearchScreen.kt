package com.example.news.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.core.presentation.common.ArticlesList
import com.example.news.core.presentation.common.SearchBar
import com.example.news.core.presentation.navigation.Route
import com.example.news.core.util.Dimens.MediumPadding_24
import com.example.news.search.presentation.mvi.SearchIntent
import com.example.news.search.presentation.mvi.SearchState


@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchIntent) -> Unit,
    navigateToDetails: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MediumPadding_24)
            .statusBarsPadding()
    ) {
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchIntent.UpdateSearchQuery(it)) },
            onSearch = { event(SearchIntent.SearchNews) }
        )
        Spacer(modifier = Modifier.height(MediumPadding_24))

        state.articles?.let { articles ->
            ArticlesList(articles = articles.collectAsLazyPagingItems()) {
                navigateToDetails(Route.DetailsScreen.route)
            }
        }

    }

}