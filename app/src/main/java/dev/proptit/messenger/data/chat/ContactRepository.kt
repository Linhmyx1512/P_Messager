package dev.proptit.messenger.data.chat

import dev.proptit.messenger.MyApp
import dev.proptit.messenger.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepository {

    suspend fun getAllContact(): List<Contact> {
        return withContext(Dispatchers.IO) {
            MyApp.getInstance().database.contactDao().getAllContact()
        }
    }

    suspend fun addDefaultContacts() {
        val defaultContacts = listOf(
            Contact(0, "Martin Randolph", R.drawable.image_ps1, false),
            Contact(0, "Andrew Parker", R.drawable.image_ps2, true),
            Contact(0, "Karen Castillo", R.drawable.image_ps3, false),
            Contact(0, "Maisy Humphrey", R.drawable.image_ps4, false),
        )
        withContext(Dispatchers.IO) {
            defaultContacts.forEach {
                MyApp.getInstance().database.contactDao().addContact(it)
            }
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

}