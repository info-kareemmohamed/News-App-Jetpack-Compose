package com.example.news.details.presentation.mvi


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.details.domain.usecase.GetArticleByUrlUseCase
import com.example.news.details.domain.usecase.UpsertArticleUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val upsertArticleUseCase: UpsertArticleUseCase,
    private val getArticleByUrlUseCase: GetArticleByUrlUseCase
) : ViewModel() {
    private val _state = mutableStateOf(DetailsState())
    val state: State<DetailsState> = _state

    init {
        onIntent(DetailsIntent.LoadArticle)
    }

    fun onIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.OnBookmarkClick -> {
                _state.value = _state.value.copy(isBookmarked = !_state.value.isBookmarked)
            }

            DetailsIntent.LoadArticle -> loadArticle()
            DetailsIntent.SaveFinalBookmarkInData -> saveFinalBookmark()
        }
    }


    private fun saveFinalBookmark() {
        viewModelScope.launch {
            _state.value.article?.let { article ->
                upsertArticleUseCase(article.copy(isBookMarked = _state.value.isBookmarked))
            }
        }
    }


    private fun loadArticle() {
        viewModelScope.launch {
            val articleUrl = savedStateHandle.get<String>("articleUrl")
            if (articleUrl.isNullOrEmpty()) return@launch
            val article = getArticleByUrlUseCase(articleUrl)?: return@launch
            _state.value = _state.value.copy(article = article)

        }
    }

}