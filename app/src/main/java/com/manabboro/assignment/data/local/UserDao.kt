package com.manabboro.assignment.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.manabboro.movie.data.model.PendingUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: PendingUser)

    @Query("SELECT * FROM pending_users WHERE isSynced = 0")
    suspend fun getPendingUsers(): List<PendingUser>

    @Update
    suspend fun updateUser(user: PendingUser)

    @Delete
    suspend fun deleteUser(user: PendingUser)
}
