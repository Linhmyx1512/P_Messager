package dev.proptit.messenger.data.repository

import dev.proptit.messenger.data.local.dao.MessageDao
import dev.proptit.messenger.data.local.entity.MessageEntity
import dev.proptit.messenger.data.maper.MessageMapper
import dev.proptit.messenger.domain.model.Message
import dev.proptit.messenger.domain.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(private val messageDao: MessageDao) :
    MessageRepository {
    override suspend fun addMessage(message: Message): Long {
        return withContext(Dispatchers.IO) {
            messageDao.addMessage(MessageMapper.getMessageEntityFromMessage(message))
        }
    }

    override suspend fun addListMessage(messages: List<MessageEntity>) {
        return withContext(Dispatchers.IO) {
            messageDao.addListMessage(messages)
        }
    }

    override suspend fun getMessageByContactId(id: Int, myId: Int): Flow<List<Message>> {
        return withContext(Dispatchers.IO) {
            messageDao.getMessageByContactId(id, myId).map { allMessages ->
                allMessages.map { MessageMapper.getMessageFromMessageEntity(it) }
            }
        }
    }

    override suspend fun getAllMessages(): Flow<List<Message>> {
        return withContext(Dispatchers.IO) {
            messageDao.getAllMessages().map { allMessages ->
                allMessages.map { MessageMapper.getMessageFromMessageEntity(it) }
            }
        }
    }
}