package com.example.news.details.presentation.mvi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle
):AndroidViewModel(application)  {
}