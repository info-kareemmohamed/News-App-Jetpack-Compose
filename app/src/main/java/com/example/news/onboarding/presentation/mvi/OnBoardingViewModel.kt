package com.example.news.onboarding.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.core.domain.usecase.SaveAppEntryUseCase
import com.example.news.onboarding.data.PageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val pageData: PageData,
    private val saveAppEntryUseCase: SaveAppEntryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(OnBoardingState())
    val state = _state.asStateFlow()


    init {
        onIntent(OnBoardingIntent.LoadPages)
    }

    fun onIntent(intent: OnBoardingIntent) {
        when (intent) {
            OnBoardingIntent.LoadPages -> _state.value = _state.value.copy(pages = pageData.pages)
            is OnBoardingIntent.OnChangePage ->  changePage(intent.currentPage)
            OnBoardingIntent.SaveAppEntry -> saveAppEntry()
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            saveAppEntryUseCase()
        }
    }

    private fun changePage(value: Int) {
        val newPage = (value).coerceIn(0, pageData.pages.size - 1)
        _state.value = _state.value.copy(
            currentPage = newPage,
            isLastPage = newPage == pageData.pages.size - 1
        )
    }
}


