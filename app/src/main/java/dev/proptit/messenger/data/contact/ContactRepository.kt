package dev.proptit.messenger.data.contact

import dev.proptit.messenger.MyApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepository {

    suspend fun getAllContact(): List<Contact> {
        return withContext(Dispatchers.IO) {
            MyApp.getInstance().database.contactDao().getAllContact()
        }
    }

    suspend fun getContactById(id: Int): Contact {
        return withContext(Dispatchers.IO) {
            MyApp.getInstance().database.contactDao().getContactById(id)
        }
    }

    suspend fun addContact(contact: Contact): Long {
        return withContext(Dispatchers.IO) {
            MyApp.getInstance().database.contactDao().addContact(contact)
        }
    }

    suspend fun addListContact(contacts: List<Contact>) {
        return withContext(Dispatchers.IO) {
            MyApp.getInstance().database.contactDao().addListContact(contacts)
        }
    }

}