package com.ashish.cleanjetpackhilt.features.movies.ui

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.cleanjetpackhilt.common.doOnFailure
import com.ashish.cleanjetpackhilt.common.doOnLoading
import com.ashish.cleanjetpackhilt.common.doOnSuccess
import com.ashish.cleanjetpackhilt.data.database.AppDatabaseImpl
import com.ashish.cleanjetpackhilt.data.model.MovieDetail
import com.ashish.cleanjetpackhilt.features.movies.domain.model.MovieUiModel
import com.ashish.cleanjetpackhilt.features.movies.domain.usecase.MovieUseCase
import com.ashish.cleanjetpackhilt.utils.Constants.keyName
import com.ashish.cleanjetpackhilt.utils.Constants.loading
import com.ashish.cleanjetpackhilt.utils.Constants.noData
import com.ashish.cleanjetpackhilt.utils.convertListToJson
import com.ashish.cleanjetpackhilt.utils.toDataList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val sharedPreferences: SharedPreferences,
    private val appDatabaseImpl: AppDatabaseImpl
):ViewModel() {

    private val _movieResponse:MutableState<MovieState> = mutableStateOf(MovieState())
    val movieResponse:State<MovieState> = _movieResponse

    private val _movieDetails:MutableState<MovieDetailState> = mutableStateOf(MovieDetailState())
    val movieDetails:MutableState<MovieDetailState> = _movieDetails

    fun getRecentMovies(): MutableList<MovieDetail> {
        return sharedPreferences.getString(keyName, "").toDataList(MovieDetail::class.java)
    }


    fun getMoviesDetails(movie: String?) = viewModelScope.launch {
        appDatabaseImpl.getMovieDetailById(movie.orEmpty())?.let {
            updateMovieDetailData(it)
        } ?: run {
            movieUseCase.getMovies(movie.orEmpty())
                .doOnSuccess {
                    if (it!=null) {
                        it.imdbID = movie.orEmpty()
                        appDatabaseImpl.insertMovieDetail(it)
                        updateMovieDetailData(it)
                    }
                }.collect()
        }
    }

    private fun updateMovieDetailData(it: MovieDetail) {
        _movieDetails.value = MovieDetailState(it)
        addRecentItemData(it)
    }

    fun searchMovies(data: String) = viewModelScope.launch {
        movieUseCase.searchMovies(data)
            .doOnSuccess {
                updateSearchData(it)
            }
            .doOnFailure {
                _movieResponse.value = MovieState(
                    error = noData,
                )
            }
            .doOnLoading {
                _movieResponse.value = MovieState(
                    isLoading = true
                )
            }.collect()

    }

    private fun updateSearchData(it: List<MovieUiModel>?) {
        if (it != null)
            _movieResponse.value = MovieState(
                it,
            )
        else {
            _movieResponse.value = MovieState(
                error = noData,
            )
        }
    }

    fun resetMovieDetail() {
        _movieDetails.value = MovieDetailState(error = loading)
    }

    private fun addRecentItemData(movieDetail: MovieDetail?) {
        movieDetail?.let {
            val recentSearches = getRecentMovies()
            if (recentSearches.contains(movieDetail)) recentSearches.remove(movieDetail)
            recentSearches.add(0, movieDetail)
            if (recentSearches.size > 5) recentSearches.removeAt(recentSearches.lastIndex)
            setRecentSearchData(recentSearches)
        }
    }

    fun removeRecentItemData(suggestionName: MovieDetail?) {
        val recentSearches = getRecentMovies()
        if (recentSearches.contains(suggestionName)) {
            recentSearches.remove(suggestionName)
        }
        setRecentSearchData(recentSearches)
    }

    private fun setRecentSearchData(items: List<MovieDetail?>) {
        sharedPreferences.edit().putString(keyName, items.convertListToJson()).apply()
    }


}

