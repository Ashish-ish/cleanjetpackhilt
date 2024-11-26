package com.ashish.cleanjetpackhilt.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ashish.cleanjetpackhilt.features.movies.screens.BrowseScreen
import com.ashish.cleanjetpackhilt.features.movies.screens.MovieDetailScreen
import com.ashish.cleanjetpackhilt.features.movies.screens.MovieListScreen
import com.ashish.cleanjetpackhilt.features.movies.screens.RecentSearchHomeSearch
import com.ashish.cleanjetpackhilt.features.movies.ui.MovieViewModel
import com.ashish.cleanjetpackhilt.utils.MovieNavigationItems

@Composable
fun MovieNavigation(
    viewModel: MovieViewModel
) {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = MovieNavigationItems.MovieSearch.route
    ) {
        composable(
            route = MovieNavigationItems.MovieSearch.route,
            arguments = listOf(navArgument("defaultText") { defaultValue = "" })
        ) { backStackEntry ->
            BrowseScreen(viewModel = viewModel, navHostController = navHostController, defaultText = backStackEntry.arguments?.getString("defaultText").orEmpty())
        }

        composable(MovieNavigationItems.MovieList.route){
            MovieListScreen(viewModel = viewModel, navHostController = navHostController)
        }
        composable(MovieNavigationItems.MovieDetails.route){
            MovieDetailScreen(viewModel, navHostController)
        }
        composable(MovieNavigationItems.RecentSearch.route){
            RecentSearchHomeSearch(viewModel, navHostController)
        }

    }

}