package com.manabboro.assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manabboro.movie.data.model.PendingUser

@Database(entities = [PendingUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
