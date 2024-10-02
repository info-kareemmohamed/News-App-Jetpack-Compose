package com.example.news.onboarding.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news.core.presentation.common.NewsButton
import com.example.news.core.presentation.common.NewsTextButton
import com.example.news.onboarding.presentation.Dimens.MediumPadding1
import com.example.news.onboarding.presentation.components.OnBoardingPage
import com.example.news.onboarding.presentation.components.PagerIndicator
import com.example.news.onboarding.presentation.mvi.OnBoardingIntent
import com.example.news.onboarding.presentation.mvi.OnBoardingViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value
    val pagerState = rememberPagerState(state.currentPage) { state.pages.size }

    // Update the pager state when the current page in the state changes
    LaunchedEffect(state.currentPage) {
        pagerState.animateScrollToPage(state.currentPage)
    }

    // Update ViewModel when page changes
    LaunchedEffect(pagerState.currentPage) {
        viewModel.onIntent(OnBoardingIntent.OnChangePage(pagerState.currentPage))
    }



    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(page = state.pages[index])
        }
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MediumPadding1)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PagerIndicator(
                modifier = Modifier.width(52.dp),
                pagesCount = state.pages.size,
                currentPage = pagerState.currentPage
            )
            Row {
                if (state.currentPage > 0) {
                    NewsTextButton(text = "Back") {
                        viewModel.onIntent(OnBoardingIntent.OnChangePage(state.currentPage - 1))
                    }
                }
                NewsButton(text = if (state.isLastPage) "Get Started" else "Next") {
                    if (state.isLastPage) {
                        //TODO Navigate to Home Screen
                    } else {
                        viewModel.onIntent(OnBoardingIntent.OnChangePage(state.currentPage + 1))
                    }
                }

            }

        }
        Spacer(modifier = Modifier.weight(0.5f))


    }


}


