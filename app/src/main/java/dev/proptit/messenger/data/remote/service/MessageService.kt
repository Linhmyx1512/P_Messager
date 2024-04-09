package dev.proptit.messenger.data.remote.service

import dev.proptit.messenger.data.remote.dto.MessageCreateInputDto
import dev.proptit.messenger.data.remote.dto.MessageOutputDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageService {
    @POST("api/message/create")
    fun createMessage(@Body message: MessageCreateInputDto): Call<MessageOutputDto>

    @GET("api/message/{idContact}")
    fun findAllMessageWithContactId(@Path("idContact") idContact: Int): Call<List<MessageOutputDto>>
}