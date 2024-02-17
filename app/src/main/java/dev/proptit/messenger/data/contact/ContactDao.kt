package dev.proptit.messenger.data.contact

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    suspend fun getAllContact(): List<Contact>

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun getContactById(id: Int): Contact

    @Insert(entity = Contact::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContact(contact: Contact): Long

}