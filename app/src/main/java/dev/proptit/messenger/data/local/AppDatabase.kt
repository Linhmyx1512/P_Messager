package dev.proptit.messenger.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.proptit.messenger.data.local.dao.ContactDao
import dev.proptit.messenger.data.local.dao.MessageDao
import dev.proptit.messenger.data.local.entity.ContactEntity
import dev.proptit.messenger.data.local.entity.MessageEntity


@Database(
    entities = [ContactEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun contactDao(): ContactDao
}