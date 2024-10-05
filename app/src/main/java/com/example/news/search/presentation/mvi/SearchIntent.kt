package com.example.news.search.presentation.mvi

sealed class SearchIntent {

    data class UpdateSearchQuery(val searchQuery: String) : SearchIntent()
    object SearchNews: SearchIntent()

}