package com.ashish.cleanjetpackhilt.features.movies.domain.repository

import com.ashish.cleanjetpackhilt.common.Result
import com.ashish.cleanjetpackhilt.data.model.MovieDetail
import com.ashish.cleanjetpackhilt.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(name: String):Flow<Result<MovieDetail>>

    suspend fun searchMovies(data: String):Flow<Result<MovieResponse>>


}