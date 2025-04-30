package com.manabboro.assignment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.manabboro.assignment.data.remote.UserPagingSource
import com.manabboro.assignment.data.remote.UserApiService
import com.manabboro.assignment.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {

    override fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { UserPagingSource(apiService) }
        ).flow
    }
}
