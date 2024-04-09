package dev.proptit.messenger.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ContactLoginInputDto(
    @SerializedName("username")
    val username: String = "",
    @SerializedName("password")
    val password: String = ""
)
