package com.example.news.home.data.remote.dto

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)