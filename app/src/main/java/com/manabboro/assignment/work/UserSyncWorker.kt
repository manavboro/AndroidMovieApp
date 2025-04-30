package com.manabboro.assignment.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.manabboro.assignment.repository.AddUserRepository

class UserSyncWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: AddUserRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val pendingUsers = repository.getPendingUsers()
        for (user in pendingUsers) {
            try {
                val response = repository.addUserOnline(user.name, user.job)
                repository.updateUser(user.copy(serverId = response.id.toInt(), isSynced = true))
            } catch (e: Exception) {
                return Result.retry()
            }
        }
        return Result.success()
    }
}
