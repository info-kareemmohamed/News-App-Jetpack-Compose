package com.example.news.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.home.presentation.HomeScreen
import com.example.news.home.presentation.HomeViewModel
import com.example.news.onboarding.presentation.OnBoardingScreen

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
                val viewModel:HomeViewModel = hiltViewModel()
                 HomeScreen(articles =viewModel.news.collectAsLazyPagingItems(),{})




            }

        }

    }
}