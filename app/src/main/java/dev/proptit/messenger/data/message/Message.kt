package dev.proptit.messenger.data.message

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val message: String,
    val idSendContact: Int,
    val idReceiveContact: Int,
    val time: Long = System.currentTimeMillis()
)
