package com.manabboro.movie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_users")
data class PendingUser(
    @PrimaryKey(autoGenerate = true) val localId: Int = 0,
    val name: String,
    val job: String,
    val serverId: Int? = null, // Updated after sync
    val isSynced: Boolean = false
)
