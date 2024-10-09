package com.example.news.core.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.news.R
import com.example.news.bookmark.presentation.BookmarkScreen
import com.example.news.bookmark.presentation.BookmarkViewModel
import com.example.news.details.presentation.DetailsScreen
import com.example.news.details.presentation.mvi.DetailsViewModel
import com.example.news.home.presentation.HomeScreen
import com.example.news.search.presentation.SearchScreen
import com.example.news.search.presentation.mvi.SearchViewModel

@Composable
fun NewsNavigator() {

    val bottomNavigationItems = listOf(
        R.drawable.ic_home,
        R.drawable.ic_search,
        R.drawable.ic_bookmark_false
    )
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItem = when (backStackEntry?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> 0
    }
    // Determine if the bottom bar should be visible based on the current route
    val isBottomBarVisible = remember(backStackEntry) {
        backStackEntry?.destination?.route in listOf(
            Route.HomeScreen.route,
            Route.SearchScreen.route,
            Route.BookmarkScreen.route
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    selected = selectedItem,
                    icons = bottomNavigationItems,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(navController, Route.HomeScreen.route)
                            1 -> navigateToTab(navController, Route.SearchScreen.route)
                            2 -> navigateToTab(navController, Route.BookmarkScreen.route)
                        }

                    })

            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(Route.HomeScreen.route) {
                HomeScreen(
                    navigateToSearch = {
                        navigateToTab(navController, Route.SearchScreen.route)
                    },
                    navigateToDetails = { articleUrl ->
                        navigateToDetails(navController, articleUrl)
                    }
                )
            }
            composable(Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(state = state, event = viewModel::onIntent) { articleUrl ->
                    navigateToDetails(navController, articleUrl)
                }
            }

            composable(
                route = Route.DetailsScreen.route,
                arguments = Route.DetailsScreen.arguments
            ) {
                val viewModel: DetailsViewModel = hiltViewModel()
                DetailsScreen(state = viewModel.state.value, event = viewModel::onIntent) {
                    navController.navigateUp()
                }
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                BookmarkScreen(articles = viewModel.articles.value) { articleUrl ->
                    navigateToDetails(navController, articleUrl)
                }
            }
        }
    }


}


private fun navigateToDetails(navController: NavController, articleUrl: String) {
    val encodedUrl = Uri.encode(articleUrl) // Encode the URL to handle special characters
    navController.navigate(
        route =
        Route.DetailsScreen.route.replace(
            "{articleUrl}",
            encodedUrl
        )
    )
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }

        }
        restoreState = true
        launchSingleTop = true

    }
}