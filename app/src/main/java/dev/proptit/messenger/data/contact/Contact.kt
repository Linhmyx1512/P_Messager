package dev.proptit.messenger.data.contact

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @DrawableRes val imageId:Int,
    val isSent: Boolean
)

