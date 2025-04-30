package com.manabboro.assignment.ui.adduser

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.manabboro.assignment.repository.AddUserRepository
import com.manabboro.assignment.utils.NetworkUtils
import com.manabboro.assignment.work.UserSyncWorker
import com.manabboro.movie.data.model.PendingUser

import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val repository: AddUserRepository,
    @ApplicationContext private val context: Context

) : ViewModel() {

    fun addUser(name: String, job: String) {
        viewModelScope.launch {
            if (NetworkUtils.isInternetAvailable(context)) {
                try {
                    val response = repository.addUserOnline(name, job)
                    // Handle success (maybe show Toast or navigate back)
                } catch (e: Exception) {
                    // Handle error
                }
            } else {
                repository.saveUserOffline(
                    PendingUser(name = name, job = job)
                )
                enqueueUserSyncWorker()

            }
        }
    }

    private fun enqueueUserSyncWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncWorkRequest = OneTimeWorkRequestBuilder<UserSyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(syncWorkRequest)

    }


}
