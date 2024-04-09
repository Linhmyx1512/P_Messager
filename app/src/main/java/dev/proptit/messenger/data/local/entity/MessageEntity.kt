package dev.proptit.messenger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val message: String,
    val idSendContact: Int,
    val idReceiveContact: Int,
    val time: Long = System.currentTimeMillis(),
)
