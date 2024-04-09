package dev.proptit.messenger.data.maper

import dev.proptit.messenger.data.local.entity.MessageEntity
import dev.proptit.messenger.data.remote.dto.MessageOutputDto
import dev.proptit.messenger.domain.model.Message

object MessageMapper {
    fun getListMessageEntityFromListMessageOutputDto(messageOutputDto: List<MessageOutputDto>?): List<MessageEntity> {
        val messages = mutableListOf<MessageEntity>()
        messageOutputDto?.forEach {
            messages.add(
                MessageEntity(
                    id = it.id,
                    idSendContact = it.idSendContact,
                    idReceiveContact = it.idReceiveContact,
                    message = it.message,
                    time = it.time
                )
            )
        }
        return messages
    }

    fun getMessageEntityFromMessage(message: Message): MessageEntity {
        return MessageEntity(
            id = message.id,
            idSendContact = message.idSendContact,
            idReceiveContact = message.idReceiveContact,
            message = message.message,
            time = message.time
        )
    }

    fun getMessageFromMessageEntity(messageEntity: MessageEntity): Message {
        return Message(
            id = messageEntity.id,
            idSendContact = messageEntity.idSendContact,
            idReceiveContact = messageEntity.idReceiveContact,
            message = messageEntity.message,
            time = messageEntity.time
        )
    }
}