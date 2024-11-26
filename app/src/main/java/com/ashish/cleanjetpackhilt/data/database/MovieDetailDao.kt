package com.ashish.cleanjetpackhilt.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashish.cleanjetpackhilt.data.model.MovieDetail

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetail)

    @Query("SELECT * FROM movie_details WHERE imdbID = :imdbID")
    suspend fun getMovieByImdb(imdbID: String): MovieDetail?

}