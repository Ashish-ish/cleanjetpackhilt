package com.ashish.cleanjetpackhilt.features.movies.domain.usecase

import com.ashish.cleanjetpackhilt.common.Result
import com.ashish.cleanjetpackhilt.common.map
import com.ashish.cleanjetpackhilt.data.model.MovieDetail
import com.ashish.cleanjetpackhilt.features.movies.domain.mapper.MovieMapper
import com.ashish.cleanjetpackhilt.features.movies.domain.model.MovieUiModel
import com.ashish.cleanjetpackhilt.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieMapper:MovieMapper
) {

    suspend fun getMovies(name: String): Flow<Result<MovieDetail?>> {
        return movieRepository.getMovies(name).map { result->
            result.map {
                it
            }
        }
    }

    suspend fun searchMovies(data: String): Flow<Result<List<MovieUiModel>?>> {
        return movieRepository.searchMovies(data).map { result->
            result.map {
                movieMapper.mapFrom(it)
            }
        }
    }

}