package dev.proptit.messenger.domain.repository

import dev.proptit.messenger.data.local.entity.ContactEntity
import dev.proptit.messenger.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getAllContact(): Flow<List<Contact>>
    suspend fun getContactByUserId(myId: Int): Flow<List<Contact>>
    suspend fun addContact(contact: Contact): Long
    suspend fun addListContact(contacts: List<ContactEntity>)
    suspend fun getById(id: Int): Contact
}