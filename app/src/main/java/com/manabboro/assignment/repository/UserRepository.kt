package com.manabboro.assignment.repository

import androidx.paging.PagingData
import com.manabboro.assignment.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<PagingData<User>>
}
