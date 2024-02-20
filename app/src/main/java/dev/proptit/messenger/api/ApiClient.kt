package dev.proptit.messenger.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://34.124.219.119:8080/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val contactService: ContactService by lazy {
        retrofit.create(ContactService::class.java)
    }
    val messageService: MessageServer by lazy {
        retrofit.create(MessageServer::class.java)
    }
}