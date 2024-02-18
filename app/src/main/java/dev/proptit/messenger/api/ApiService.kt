package dev.proptit.messenger.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // register
    @POST("contact/register")
    fun register(@Body data: RegisterData): Call<Int>

    // login
    @POST("contact/login")
    fun login(@Body data: LoginData): Call<Int>
}

data class RegisterData(val name: String, val avatar: String, val username: String, val password: String)
data class LoginData(val username: String, val password: String)
