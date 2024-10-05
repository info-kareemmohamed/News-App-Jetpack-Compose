package com.example.news.core.presentation.navgraph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.home.presentation.HomeScreen
import com.example.news.home.presentation.HomeViewModel
import com.example.news.onboarding.presentation.OnBoardingScreen
import com.example.news.search.presentation.SearchScreen
import com.example.news.search.presentation.mvi.SearchViewModel

@Composable
fun NavGraph(
    startDestination: String,

    ) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ) {
                OnBoardingScreen()
            }
        }
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ) {

            composable(
                route = Route.NewsNavigatorScreen.route
            ) {
                val viewModel: SearchViewModel = hiltViewModel()
                SearchScreen(
                    state = viewModel.state.value,
                    event = viewModel::onIntent,
                    navigateToDetails = {}
                )
            }
        }

    }

}