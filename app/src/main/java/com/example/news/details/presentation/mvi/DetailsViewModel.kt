package com.example.news.details.presentation.mvi


import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.details.domain.usecase.GetArticleByUrlUseCase
import com.example.news.details.domain.usecase.UpdateBookmarkStatusUseCase
import com.example.news.details.domain.usecase.UpsertArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateBookmarkStatusUseCase: UpdateBookmarkStatusUseCase,
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
        _state.value.article?.takeIf { it.isBookMarked != _state.value.isBookmarked }?.let { article ->
            viewModelScope.launch {
                updateBookmarkStatusUseCase(article.url, _state.value.isBookmarked)
            }
        }
    }


    private fun loadArticle() {
        viewModelScope.launch {
            savedStateHandle.get<String>("articleUrl")?.let { Uri.decode(it) }?.let { articleUrl ->
                getArticleByUrlUseCase(articleUrl)?.let { article ->
                    _state.value = _state.value.copy(article = article, isBookmarked = article.isBookMarked)
                }
            }
        }
    }

}