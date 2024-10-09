package com.example.news.details.presentation.mvi


import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.core.domain.model.SourceType
import com.example.news.details.domain.usecase.DeleteArticleUseCase
import com.example.news.details.domain.usecase.GetArticleByUrlUseCase
import com.example.news.details.domain.usecase.UpdateBookmarkStatusUseCase
import com.example.news.details.domain.usecase.UpsertArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val upsertArticleUseCase: UpsertArticleUseCase,
    private val getArticleByUrlUseCase: GetArticleByUrlUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
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
        _state.value.article?.takeIf { it.isBookMarked != _state.value.isBookmarked }
            ?.let { article ->
                viewModelScope.launch {
                    if (_state.value.isBookmarked)
                        upsertArticleUseCase(article.copy(isBookMarked = true, sourceType = SourceType.BOOKMARK))
                    else deleteArticleUseCase(article)
                }
            }
    }


    private fun loadArticle() {
        viewModelScope.launch {
            savedStateHandle.get<String>("articleUrl")?.let { Uri.decode(it) }?.let { articleUrl ->
                println("articleUrl: $articleUrl")
                getArticleByUrlUseCase(articleUrl)?.let { article ->
                    println("article: $article")
                    _state.value =
                        _state.value.copy(article = article, isBookmarked = article.isBookMarked)
                }
            }
        }
    }

}