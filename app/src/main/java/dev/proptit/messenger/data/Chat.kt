package dev.proptit.messenger.data

import androidx.annotation.DrawableRes

data class Chat(
    val id: Int,
    val name: String,
    val message: String,
    val time: String,
    @DrawableRes val imageId:Int,
    val isSent: Boolean
)

