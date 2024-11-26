package com.ashish.cleanjetpackhilt.data.network

import com.ashish.cleanjetpackhilt.data.model.MovieDetail
import com.ashish.cleanjetpackhilt.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://omdbapi.com/"
    }

    @GET("?apikey=a0783fa9")
    suspend fun searchMovies(@Query("s") search: String): Response<MovieResponse>

    @GET("?apikey=a0783fa9")
    suspend fun getMovieDetail(@Query("i") imdbID: String): Response<MovieDetail>
}