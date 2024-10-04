package com.example.news.home.presentation


data class HomeState(
    val newsTicker: String = "",
    val isLoading: Boolean = false,
)