package dev.proptit.messenger.data.message

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {
    @Insert(entity = Message::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: Message): Long

    // add list messages
    @Insert(entity = Message::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListMessage(messages: List<Message>)
    @Query("SELECT * FROM message WHERE id = :id")
    suspend fun getMessageById(id: Int): Message

    @Query("SELECT * FROM message WHERE idReceiveContact = :idReceive")
    suspend fun getMessageByReceiveId(idReceive: Int): List<Message>

    @Query("SELECT * FROM message WHERE idSendContact = :idSend")
    suspend fun getMessageBySendId(idSend: Int): List<Message>

    @Query("SELECT * FROM MESSAGE WHERE idReceiveContact = :contactId OR idSendContact = :contactId")
    suspend fun getMessageByContactId(contactId: Int): List<Message>

}