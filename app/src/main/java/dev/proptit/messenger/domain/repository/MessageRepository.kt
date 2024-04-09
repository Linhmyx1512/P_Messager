package dev.proptit.messenger.domain.repository

import dev.proptit.messenger.data.local.entity.MessageEntity
import dev.proptit.messenger.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun addMessage(message: Message): Long
    suspend fun addListMessage(messages: List<MessageEntity>)
    suspend fun getMessageByContactId(id: Int, myId: Int): Flow<List<Message>>
    suspend fun getAllMessages(): Flow<List<Message>>
}