package com.example.news.details.presentation.mvi

import com.example.news.core.domain.model.Article

data class DetailsState (
    val article: Article? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isBookmarked: Boolean = false

)