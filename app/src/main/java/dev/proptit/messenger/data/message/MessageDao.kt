package dev.proptit.messenger.data.message

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {
    @Insert(entity = Message::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: Message): Long

    @Query("SELECT * FROM message WHERE id = :id")
    suspend fun getMessageById(id: Int): Message

    @Query("SELECT * FROM message WHERE idReceive = :idReceive")
    suspend fun getMessageByReceiveId(idReceive: Int): List<Message>

    @Query("SELECT * FROM MESSAGE WHERE idReceive = :contactId OR idSend = :contactId")
    suspend fun getMessageByContactId(contactId: Int): List<Message>

    @Query("SELECT * FROM message")
    suspend fun getAllMessage(): List<Message>
}