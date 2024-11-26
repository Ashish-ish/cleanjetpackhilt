package com.ashish.cleanjetpackhilt.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashish.cleanjetpackhilt.data.model.MovieResponse

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchResults(movieResponse: MovieResponse)

    @Query("SELECT * FROM search_movies WHERE searchKeyword = :keyword")
    suspend fun getSearchResultsByKeyword(keyword: String): MovieResponse?

}