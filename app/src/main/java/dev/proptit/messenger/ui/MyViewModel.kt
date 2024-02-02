package dev.proptit.messenger.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.proptit.messenger.data.chat.Contact
import dev.proptit.messenger.data.chat.ContactRepository
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.data.message.MessageRepository
import kotlinx.coroutines.launch

class MyViewModel(
    private val contactRepository: ContactRepository,
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _messageList = MutableLiveData<List<Message>>(emptyList())
    val messageList: LiveData<List<Message>>
        get() = _messageList

    private val _contactList = MutableLiveData<List<Contact>>()
    val contactList: LiveData<List<Contact>>
        get() = _contactList


    init {
        viewModelScope.launch {
            contactRepository.addDefaultContacts()
            getAllContact()
        }
    }

    // message
//    fun getLastMessageByContactId(id: Int) : Message = viewModelScope.launch {
//        messageRepository.getMessageByContactId(id).maxByOrNull { it.id }
//    }

    fun addMessage(message: Message) {
        viewModelScope.launch {
            if (messageRepository.addMessage(message) > 0) {
                getAllMessage()
            }
        }
    }

    fun getMessageByReceiveId(idSend: Int) = viewModelScope.launch {
        messageRepository.getMessageByReceiveId(idSend)
    }

    private fun getAllMessage() {
        viewModelScope.launch {
            _messageList.postValue(messageRepository.getAllMessage())
        }
    }

    fun getMessageById(id: Int) = viewModelScope.launch {
        messageRepository.getMessageById(id)
    }

    // contact
    private fun getAllContact() {
        viewModelScope.launch {
            _contactList.postValue(contactRepository.getAllContact())
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            if (contactRepository.addContact(contact) > 0) {
                getAllContact()
            }
        }
    }

    fun getContactById(id: Int) = viewModelScope.launch {
        contactRepository.getContactById(id)
    }

}

class MyViewModelFactory(
    private val contactRepository: ContactRepository,
    private val messageRepository: MessageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(contactRepository, messageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}