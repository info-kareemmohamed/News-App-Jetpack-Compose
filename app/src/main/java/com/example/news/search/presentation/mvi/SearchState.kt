package com.example.news.search.presentation.mvi

import androidx.paging.PagingData
import com.example.news.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState (
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)