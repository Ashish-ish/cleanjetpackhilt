package com.ashish.cleanjetpackhilt.utils

sealed class MovieNavigationItems(val route: String) {

    data object MovieSearch : MovieNavigationItems("movieSearch/{defaultText}") {
        fun createRoute(defaultText: String) = "movieSearch/$defaultText"
    }
    data object MovieList : MovieNavigationItems("movieList")
    data object MovieDetails : MovieNavigationItems("movieDetails")
    data object RecentSearch : MovieNavigationItems("recentSearch")
}
