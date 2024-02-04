package dev.proptit.messenger.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    val conversationList
        get() = _conversationList


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