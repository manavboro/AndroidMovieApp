package com.manabboro.assignment.data.remote

import com.manabboro.assignment.model.UserPostRequest
import com.manabboro.assignment.model.UserResponse
import com.manabboro.assignment.model.UserResponsePost
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int
    ): UserResponse

    @POST("users")
    suspend fun addUser(@Body request: UserPostRequest): UserResponsePost

}
