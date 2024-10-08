package com.example.news.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.news.core.presentation.navigation.NavGraph
import com.example.news.core.presentation.ui.theme.NewsTheme
import com.example.news.core.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition{
            viewModel.splashCondition.value
        }
        setContent {
            NewsTheme {
                Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                   NavGraph(startDestination = viewModel.startDestination.value)
                }
            }
        }
    }
}
