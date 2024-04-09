package dev.proptit.messenger.domain.model

data class Message(
    val id: Int = 0,
    val message: String,
    val idSendContact: Int,
    val idReceiveContact: Int,
    val time: Long = System.currentTimeMillis(),
)
