package com.ashish.cleanjetpackhilt.data.repository

import com.ashish.cleanjetpackhilt.common.Result
import com.ashish.cleanjetpackhilt.common.base.BaseRepository
import com.ashish.cleanjetpackhilt.data.model.MovieDetail
import com.ashish.cleanjetpackhilt.data.model.MovieResponse
import com.ashish.cleanjetpackhilt.data.network.ApiService
import com.ashish.cleanjetpackhilt.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val apiService: ApiService,
) : BaseRepository() , MovieRepository {

    override suspend fun getMovies(name: String): Flow<Result<MovieDetail>> = safeApiCall {
        apiService.getMovieDetail(name)
    }

    override suspend fun searchMovies(data: String): Flow<Result<MovieResponse>> = safeApiCall {
        apiService.searchMovies(data)
    }
}