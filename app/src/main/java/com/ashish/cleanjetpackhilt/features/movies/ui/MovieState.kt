package com.ashish.cleanjetpackhilt.features.movies.ui

import com.ashish.cleanjetpackhilt.features.movies.domain.model.MovieUiModel

data class MovieState(
    val data:List<MovieUiModel>? = emptyList(),
    val error:String = "",
    val isLoading:Boolean = false
)
