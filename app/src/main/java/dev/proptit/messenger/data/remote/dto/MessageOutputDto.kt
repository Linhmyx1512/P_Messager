package dev.proptit.messenger.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MessageOutputDto(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("idSendContact")
    var idSendContact: Int = 0,
    @SerializedName("idReceiveContact")
    var idReceiveContact: Int = 0,
    @SerializedName("time")
    var time: Long = 0,
)
