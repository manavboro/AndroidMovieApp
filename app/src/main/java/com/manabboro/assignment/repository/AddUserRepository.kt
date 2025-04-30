package com.manabboro.assignment.repository

import com.manabboro.assignment.data.local.UserDao
import com.manabboro.assignment.data.remote.UserApiService
import com.manabboro.assignment.model.UserPostRequest
import com.manabboro.assignment.model.UserResponsePost
import com.manabboro.movie.data.model.PendingUser
import javax.inject.Inject

class AddUserRepository @Inject constructor(
    private val apiService: UserApiService,
    private val userDao: UserDao
) {
    suspend fun addUserOnline(name: String, job: String): UserResponsePost {
        return apiService.addUser(UserPostRequest(name, job))
    }

    suspend fun saveUserOffline(pendingUser: PendingUser) {
        userDao.insertUser(pendingUser)
    }

    suspend fun getPendingUsers() = userDao.getPendingUsers()

    suspend fun updateUser(user: PendingUser) = userDao.updateUser(user)
}
