package com.example.news.home.data.remote.dto

data class NewsResponse(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)