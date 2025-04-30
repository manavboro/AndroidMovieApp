package com.manabboro.assignment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.manabboro.assignment.BuildConfig
import com.manabboro.assignment.data.remote.MovieApiService
import com.manabboro.assignment.data.remote.MoviePagingSource
import com.manabboro.assignment.model.Movie
import com.manabboro.assignment.model.MovieDetail
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApiService: MovieApiService) {
    fun getTrendingMovies(): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MoviePagingSource(movieApiService) }
        )
    }

    suspend fun getMovieDetail(movieId: String): MovieDetail {
        return movieApiService.getMovieDetails(
            movieId, apiKey = BuildConfig.API_KEY
        )
    }
}
