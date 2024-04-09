package dev.proptit.messenger.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.proptit.messenger.data.local.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAllContact(): Flow<List<ContactEntity>>

    @Query("SELECT DISTINCT c.* FROM contact c INNER JOIN message m ON c.id = m.idSendContact OR c.id = m.idReceiveContact WHERE (m.idSendContact = :myId OR m.idReceiveContact = :myId) AND c.id != :myId")
    fun getContactByUserId(myId: Int): Flow<List<ContactEntity>>


    @Insert(entity = ContactEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContact(contact: ContactEntity): Long

    @Insert(entity = ContactEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListContact(contacts: List<ContactEntity>)

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun getById(id: Int): ContactEntity
}