package com.example.news.details.presentation.mvi

sealed class DetailsIntent {
    object OnBookmarkClick : DetailsIntent()
    object OnShareClick : DetailsIntent()
    object OnBrowsingClick : DetailsIntent()
}