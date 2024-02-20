package dev.proptit.messenger.api

import dev.proptit.messenger.data.message.Message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageServer {
    @POST("message/create")
    fun createMessage(@Body data: Message): Call<Int>

    @POST("message/get_message_from_contacts")
    fun getMessageFromContacts(@Body data: MessageData): Call<List<Message>>

    @POST("message/{id}")
    fun getMessageFromIdContact(@Body data: Int): Call<List<Message>>
}

data class MessageData(val  idSendContact: Int, val idReceiveContact: Int)