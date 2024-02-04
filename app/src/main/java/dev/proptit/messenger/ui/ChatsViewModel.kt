package dev.proptit.messenger.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dev.proptit.messenger.data.chat.Contact
import dev.proptit.messenger.data.chat.ContactRepository
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.data.message.MessageRepository
import dev.proptit.messenger.setup.Keys
import kotlinx.coroutines.launch

class ChatsViewModel(
    private val contactRepository: ContactRepository,
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _conversationList = MutableLiveData<List<Pair<Contact, List<Message>>>>()
    val conversationList: LiveData<List<Pair<Contact, List<Message>>>>
        get() = _conversationList

    val contactList: LiveData<List<Contact>>
        get() = _conversationList.map {
            it.map { chatData -> chatData.first}
        }


    fun setupData() {
        viewModelScope.launch {
            val messages = messageRepository.getMessageByContactId(Keys.MY_ID)

            // group messages by contact id
            val groupMessages = messages.groupBy {
                if (it.idSend == Keys.MY_ID) // if user is sender then group by idReceive
                    it.idReceive
                else    // if user is receiver then group by idSend
                    it.idSend
            }.toList() // convert map to list pair

            val conversations = mutableListOf<Pair<Contact, List<Message>>>()

            groupMessages.forEach {(idContact, messages)->
                val contact = contactRepository.getContactById(idContact)
                conversations.add(contact to messages.sortedBy { it.time })
            }
            _conversationList.postValue(conversations)
        }
    }

    fun addNewMessage(message: Message) {
        viewModelScope.launch {
            if (messageRepository.addMessage(message) > 0) {
                val tempList = _conversationList.value?.toMutableList() ?: mutableListOf()
                val index = tempList.indexOfFirst { it.first.id == message.idReceive || it.first.id == message.idSend }
                if (index != -1) {
                    val (contact, list) = tempList[index]
                    tempList[index] = contact to (list + message)
                } else {
                    val idContact = if (message.idSend == Keys.MY_ID) message.idReceive else message.idSend
                    val contact = contactRepository.getContactById(idContact)
                    tempList.add(contact to mutableListOf(message))
                }
                _conversationList.postValue(tempList)
            }
        }
    }


    // contact



}

class ChatsViewModelFactory(
    private val contactRepository: ContactRepository,
    private val messageRepository: MessageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatsViewModel(
                contactRepository, messageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}