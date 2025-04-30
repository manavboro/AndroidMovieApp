package com.manabboro.assignment.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manabboro.assignment.BuildConfig
import com.manabboro.assignment.model.Movie

class MoviePagingSource(
    private val movieApiService: MovieApiService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response =
                movieApiService.getTrendingMovies(
                    page = page,
                    title = "batman",
                    apiKey = BuildConfig.API_KEY
                )
            LoadResult.Page(
                data = response.movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }
}
