package dev.proptit.messenger.data.repository

import dev.proptit.messenger.data.local.dao.ContactDao
import dev.proptit.messenger.data.local.entity.ContactEntity
import dev.proptit.messenger.data.maper.ContactMapper
import dev.proptit.messenger.domain.model.Contact
import dev.proptit.messenger.domain.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao
) : ContactRepository {
    override fun getAllContact(): Flow<List<Contact>> {
        return contactDao.getAllContact().map { allContacts ->
            allContacts.map { ContactMapper.getContactFromContactEntity(it) }
        }
    }

    override suspend fun getContactByUserId(myId: Int): Flow<List<Contact>> {
        return withContext(Dispatchers.IO) {
            contactDao.getContactByUserId(myId).map { allContacts ->
                allContacts.map { ContactMapper.getContactFromContactEntity(it) }
            }
        }
    }

    override suspend fun addContact(contact: Contact): Long {
        return withContext(Dispatchers.IO) {
            contactDao.addContact(ContactMapper.getContactEntityFromContact(contact))
        }
    }

    override suspend fun addListContact(contacts: List<ContactEntity>) {
        return withContext(Dispatchers.IO) {
            contactDao.addListContact(contacts)
        }
    }

    override suspend fun getById(id: Int): Contact {
        return withContext(Dispatchers.IO) {
            ContactMapper.getContactFromContactEntity(contactDao.getById(id))
        }
    }
}