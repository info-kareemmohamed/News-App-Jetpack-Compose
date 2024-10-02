package com.example.news.onboarding.presentation.mvi

import com.example.news.onboarding.data.Page

data class OnBoardingState (
    val currentPage: Int = 0,
    val pages: List<Page> = emptyList(),
    val isLastPage: Boolean = false
)