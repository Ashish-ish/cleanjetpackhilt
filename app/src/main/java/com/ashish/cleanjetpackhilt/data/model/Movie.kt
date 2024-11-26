package com.ashish.cleanjetpackhilt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

data class Movies(
    val imdbID: String?=null,
    val Title: String?=null,
    val Year: String?=null,
    val Poster: String?=null
)

data class MovieResponse(
    val Search: List<Movies>?=null,
    val totalResults: String?=null,
    val Response: String?=null
)

@Entity(tableName = "movie_details")
@JsonClass(generateAdapter = true)
data class MovieDetail(
    @PrimaryKey var imdbID: String="",
    val Title: String?=null,
    val Year: String?=null,
    val Runtime: String?=null,
    val imdbRating: String?=null,
    val Type: String?=null,
    val Genre: String?=null,
    val Plot: String?=null,
    val Director: String?=null,
    val Writer: String?=null,
    val Actors: String?=null,
    val Poster: String?=null
)
