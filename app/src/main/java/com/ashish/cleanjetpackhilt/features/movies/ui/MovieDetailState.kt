package com.ashish.cleanjetpackhilt.features.movies.ui

import com.ashish.cleanjetpackhilt.data.model.MovieDetail

data class MovieDetailState(
    val data:MovieDetail? = null,
    val error:String = "",
    val isLoading:Boolean = false
)
