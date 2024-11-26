package com.ashish.cleanjetpackhilt.data.database// AppDatabaseImpl.kt
import com.ashish.cleanjetpackhilt.data.model.MovieDetail
import javax.inject.Inject

class AppDatabaseImpl @Inject constructor(
    private val database: AppDatabase,
) {
    private val movieDetailDao = database.movieDetailDao()

    suspend fun getMovieDetailById(id: String) = movieDetailDao.getMovieByImdb(id)

    suspend fun insertMovieDetail(result: MovieDetail) = movieDetailDao.insertMovieDetail(result)

}