package com.manabboro.assignment.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.manabboro.assignment.model.MovieDetail
import com.manabboro.assignment.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movies = movieRepository.getTrendingMovies().flow.cachedIn(viewModelScope)
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?> = _movieDetail

    fun fetchMovieDetail(movieId: String) {
        viewModelScope.launch {
            try {
                val result = movieRepository.getMovieDetail(movieId)
                _movieDetail.value = result
            } catch (e: Exception) {
                // Handle error (show a message or fallback)
            }
        }
    }
}
