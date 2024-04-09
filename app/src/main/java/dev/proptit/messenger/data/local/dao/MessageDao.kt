package dev.proptit.messenger.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.proptit.messenger.data.local.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(entity = MessageEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: MessageEntity): Long

    @Insert(entity = MessageEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListMessage(messages: List<MessageEntity>)

    @Query("select * from message where (idSendContact = :myId AND idReceiveContact = :id) OR (idReceiveContact = :myId AND idSendContact = :id)")
    fun getMessageByContactId(id: Int, myId: Int): Flow<List<MessageEntity>>

    @Query("select * from message")
    fun getAllMessages(): Flow<List<MessageEntity>>

}