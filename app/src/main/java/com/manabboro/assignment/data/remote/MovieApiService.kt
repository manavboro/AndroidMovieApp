package com.manabboro.assignment.data.remote

import com.manabboro.assignment.model.MovieDetail
import com.manabboro.assignment.model.OmdbSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("/")
    suspend fun getTrendingMovies(
        @Query("s") title: String ,
        @Query("page") page: Int = 1,
        @Query("apikey") apiKey: String
    ): OmdbSearchResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") movieId: String,
        @Query("apikey") apiKey: String
    ): MovieDetail
}
