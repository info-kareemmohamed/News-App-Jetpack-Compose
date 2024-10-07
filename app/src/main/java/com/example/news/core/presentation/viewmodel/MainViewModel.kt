package com.example.news.core.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.core.domain.usecase.ReadAppEntryUseCase
import com.example.news.core.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    readAppEntryUseCase: ReadAppEntryUseCase,
) : ViewModel() {
    private val _splashCondition = mutableStateOf(true)
    val splashCondition = _splashCondition


    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination = _startDestination

    init {
        readAppEntryUseCase().onEach { startFromHomeScreen ->
            if (startFromHomeScreen) {
                _startDestination.value = Route.NewsNavigation.route
            } else {
                _startDestination.value = Route.AppStartNavigation.route
            }
            delay(600) //Without this delay, the onBoarding screen will show for a momentum.
            _splashCondition.value = false
        }.launchIn(viewModelScope)


    }

}