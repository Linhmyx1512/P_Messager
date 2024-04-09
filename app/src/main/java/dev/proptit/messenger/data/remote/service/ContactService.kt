package dev.proptit.messenger.data.remote.service

import dev.proptit.messenger.data.remote.dto.ContactLoginInputDto
import dev.proptit.messenger.data.remote.dto.ContactOutputDto
import dev.proptit.messenger.data.remote.dto.ContactRegisterInputDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ContactService {

    @POST("contact/register")
    fun register(@Body data: ContactRegisterInputDto): Call<Int>

    @POST("contact/login")
    fun login(@Body data: ContactLoginInputDto): Call<Int>

    @GET("api/contact/get_contact_with_user/{idContact}")
    fun findAllContactWithContactId(@Path("idContact") idContact: Int): Call<List<ContactOutputDto>>

    @GET("api/contact")
    fun getAllContact(): Call<List<ContactOutputDto>>
}
