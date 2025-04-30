package com.manabboro.assignment.model

import com.google.gson.annotations.SerializedName

data class OmdbSearchResponse(
    @SerializedName("Search") val movies: List<Movie>,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String,
    @SerializedName("Error") val error: String? = null
)