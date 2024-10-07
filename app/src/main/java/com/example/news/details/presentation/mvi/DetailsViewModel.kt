package com.example.news.details.presentation.mvi


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
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
            DetailsIntent.SaveFinalBookmarkInData -> saveFinalBookmarkInData()
        }
    }

    private fun saveFinalBookmarkInData() {
        //TODO save final bookmark in data
    }

    private fun loadArticle() {
        val articleId = savedStateHandle.get<Int>("articleId")
        //TODO get article from database
    }

}