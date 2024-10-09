package com.example.news.home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.home.domain.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> get() = _state

    init {
        loadArticles()
    }

    // Load articles from the use case
     fun loadArticles() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val articles = getNewsUseCase(sources = listOf("bbc-news", "abc-news", "al-jazeera-english"))
            _state.value = _state.value.copy(isLoading = false, articles = articles)
        }
    }


}