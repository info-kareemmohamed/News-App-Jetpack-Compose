package com.example.news.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.R
import com.example.news.core.presentation.common.SearchBar
import com.example.news.core.util.Dimens.MediumPadding_24
import com.example.news.core.presentation.common.ArticlesList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSearch: () -> Unit,
    navigateToDetails: (articleUrl: String) -> Unit
) {
    val articles = viewModel.state.value.articles?.collectAsLazyPagingItems()
    val titles by remember {
        derivedStateOf {
            if ((articles?.itemCount ?: 0) > 10) {
                articles?.itemSnapshotList?.items
                    ?.slice(IntRange(start = 0, endInclusive = 9))
                    ?.joinToString(separator = " \uD83D\uDFE5 ") { it.title }
            } else {
                ""
            }
        }
    }

    val pullRefreshState = rememberPullToRefreshState()
    LaunchedEffect(viewModel.state.value) {
        snapshotFlow { viewModel.state.value.isLoading }
            .collect { isLoading ->
                if (isLoading) {
                    pullRefreshState.startRefresh()
                } else {
                    pullRefreshState.endRefresh()
                }
            }
    }

    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(pullRefreshState.isRefreshing) {
            viewModel.loadArticles()
        }
    }

    Box(modifier =Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(pullRefreshState.nestedScrollConnection)
                .padding(top = MediumPadding_24)
                .statusBarsPadding()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(30.dp)
                    .padding(horizontal = MediumPadding_24)
            )

            Spacer(modifier = Modifier.height(MediumPadding_24))

            SearchBar(
                modifier = Modifier
                    .padding(horizontal = MediumPadding_24)
                    .fillMaxWidth(),
                text = "",
                readOnly = true,
                onValueChange = {},
                onSearch = {},
                onClick = {
                    navigateToSearch()
                }
            )

            Spacer(modifier = Modifier.height(MediumPadding_24))

            Text(
                text = titles ?: "", modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MediumPadding_24)
                    .basicMarquee(), fontSize = 12.sp,
                color = colorResource(id = R.color.placeholder)
            )

            Spacer(modifier = Modifier.height(MediumPadding_24))

            ArticlesList(
                modifier = Modifier.padding(horizontal = MediumPadding_24),
                articles = articles,
                onClick = { article ->
                    navigateToDetails(article.url)
                }
            )
        }
    PullToRefreshContainer(
        modifier = Modifier.align(Alignment.TopCenter),
        contentColor = MaterialTheme.colorScheme.primary  ,
        containerColor = MaterialTheme.colorScheme.background,
        state = pullRefreshState)
    }
}