package dev.proptit.messenger.api

import dev.proptit.messenger.data.contact.Contact
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ContactService {

    // register
    @POST("contact/register")
    fun register(@Body data: RegisterData): Call<Int>

    // login
    @POST("contact/login")
    fun login(@Body data: LoginData): Call<Int>

    // get contact with user
    @GET("contact/get_contact_with_user/{id}")
    fun getContactWithUser(@Path("id") idUser: Int): Call<List<Contact>>

    @GET("contact")
    fun getAllContact(): Call<List<Contact>>

    @GET("contact/{id}")
    fun getContactById(@Path("id") id: Int): Call<Contact>
}

data class RegisterData(val name: String, val avatar: String, val username: String, val password: String)
data class LoginData(val username: String, val password: String)
