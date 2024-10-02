package com.example.news.onboarding.presentation.mvi

sealed class OnBoardingIntent {
    data class OnChangePage(val currentPage: Int) : OnBoardingIntent()
    object LoadPages : OnBoardingIntent()
}