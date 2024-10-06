package com.example.news.bookmark.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.bookmark.domain.usecase.GetArticlesUseCase
import com.example.news.core.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
): ViewModel() {
     val articles = mutableStateOf(emptyList<Article>())

    init {
        viewModelScope.launch {
            getArticlesUseCase().collectLatest {
                articles.value = it
            }
        }
    }



}