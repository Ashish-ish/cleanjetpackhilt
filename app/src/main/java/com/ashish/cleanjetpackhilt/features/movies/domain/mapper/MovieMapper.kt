package com.ashish.cleanjetpackhilt.features.movies.domain.mapper

import com.ashish.cleanjetpackhilt.common.base.Mapper
import com.ashish.cleanjetpackhilt.data.model.MovieResponse
import com.ashish.cleanjetpackhilt.features.movies.domain.model.MovieUiModel
import javax.inject.Inject

class MovieMapper @Inject constructor() : Mapper<MovieResponse?, List<MovieUiModel>?> {

    override fun mapFrom(from: MovieResponse?): List<MovieUiModel>? {
        return from?.Search?.map {
            MovieUiModel(
               it
            )
        }
    }
}