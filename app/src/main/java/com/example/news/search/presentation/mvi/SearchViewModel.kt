package com.example.news.search.presentation.mvi

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.news.search.domain.usecase.SearchForNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchForNewsUseCase: SearchForNewsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state = _state



    fun onIntent(intent: SearchIntent) {
        when (intent) {
            SearchIntent.SearchNews -> searchNews()
            is SearchIntent.UpdateSearchQuery -> _state.value = _state.value.copy(searchQuery = intent.searchQuery)
        }
    }


    private fun searchNews() {
        val articles = searchForNewsUseCase(
            sources = listOf("bbc-news", "abc-news", "al-jazeera-english"),
            searchQuery = _state.value.searchQuery
        ).cachedIn(viewModelScope)
        _state.value = _state.value.copy(articles = articles)
    }

}