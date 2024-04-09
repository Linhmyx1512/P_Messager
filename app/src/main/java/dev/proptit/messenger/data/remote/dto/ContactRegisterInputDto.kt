package dev.proptit.messenger.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ContactRegisterInputDto(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("password")
    val password: String = "",
)
