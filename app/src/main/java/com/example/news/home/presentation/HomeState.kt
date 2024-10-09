package com.example.news.home.presentation

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.example.news.core.domain.model.Article
import kotlinx.coroutines.flow.Flow


data class HomeState(
    val articles: Flow<PagingData<Article>>? = null,
    val isLoading: Boolean = false,
)