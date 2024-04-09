package dev.proptit.messenger.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ContactOutputDto(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("avatar")
    var avatar: String = "",
)