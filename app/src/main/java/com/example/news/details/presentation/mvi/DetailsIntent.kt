package com.example.news.details.presentation.mvi

sealed class DetailsIntent {
    object LoadArticle : DetailsIntent()
    object OnBookmarkClick : DetailsIntent()
    object SaveFinalBookmarkInData : DetailsIntent()

}