package dev.proptit.messenger.data.message

import dev.proptit.messenger.MyApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository {

    suspend fun addMessage(message: Message): Long {
        return withContext(Dispatchers.IO) {
            // Dispatchers.IO: thực hiện các thao tác I/O bên ngoài main thread (ví dụ: đọc và ghi dữ liệu từ cơ sở dữ liệu, gọi API, ...)
            MyApp.getInstance().database.messageDao().addMessage(message)
        }
    }

    suspend fun getMessageBySendId(idSend: Int): List<Message> {
        return withContext(Dispatchers.IO) {
            MyApp.getInstance().database.messageDao().getMessageBySendId(idSend)
        }
    }

    suspend fun getMessageById(id: Int): Message {
        return withContext(Dispatchers.IO) {
            MyApp.getInstance().database.messageDao().getMessageById(id)
        }
    }

    suspend fun getMessageByContactId(contactId: Int) : List<Message> {
        return withContext(Dispatchers.IO) {
            MyApp.getInstance().database.messageDao().getMessageByContactId(contactId)
        }
    }

    suspend fun getMessageByReceiveId(idReceive: Int): List<Message> {
        return withContext(Dispatchers.IO) {
            MyApp.getInstance().database.messageDao().getMessageByReceiveId(idReceive)
        }
    }
}