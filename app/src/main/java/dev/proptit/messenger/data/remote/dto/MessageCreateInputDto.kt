package dev.proptit.messenger.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MessageCreateInputDto(
    @SerializedName("message")
    val message: String = "",
    @SerializedName("idSendContact")
    val idSendContact: Int = 0,
    @SerializedName("idReceiveContact")
    val idReceiveContact: Int = 0,
    @SerializedName("time")
    val time: Long = 0
)
