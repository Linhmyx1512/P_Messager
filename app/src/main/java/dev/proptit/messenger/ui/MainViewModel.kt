package dev.proptit.messenger.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.proptit.messenger.api.ApiClient
import dev.proptit.messenger.data.contact.Contact
import dev.proptit.messenger.data.contact.ContactRepository
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.data.message.MessageRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val contactRepository: ContactRepository,
    private val messageRepository: MessageRepository
) : ViewModel() {

    // idAccount
    private var _idAccount : Int = -1
    val idAccount: Int
        get() = _idAccount

    //api service
    private val contactService = ApiClient.contactService
    private val messageService = ApiClient.messageService

    // contact with messages
    private val _conversationList = MutableLiveData<List<Pair<Contact, List<Message>>>>()
    val conversationList: LiveData<List<Pair<Contact, List<Message>>>>
        get() = _conversationList

    // all contact
    private val _allContactList = MutableLiveData<List<Contact>>()
    val allContactList: LiveData<List<Contact>>
        get() = _allContactList


    // current contact selected
    private val _curContact = MutableLiveData<Contact>()
    val curContact: LiveData<Contact>
        get() = _curContact


    // current conversation
    private val _curConversation = MutableLiveData<List<Message>>()
    val curConversation: LiveData<List<Message>>
        get() = _curConversation


    init {

        // load all contact from local
        viewModelScope.launch {
            _allContactList.value = contactRepository.getAllContact()
            fetchAllContact()
        }
    }

    fun init() {
        viewModelScope.launch {
            fetchMessageFromIdContact()
            setListConversation()
            Log.d("MainViewModel", "init2: $idAccount")
        }
    }

    fun setIdAccount(id: Int) {
        _idAccount = id
        Log.d("MainViewModel", "init1: $idAccount")
    }

    private fun fetchAllContact() {
        val allContactCall = contactService.getAllContact()
        allContactCall.enqueue(object : Callback<List<Contact>> {
            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                if (response.isSuccessful) {
                    val contacts = response.body()
                    contacts?.let {
                        _allContactList.postValue(it)
                        viewModelScope.launch {
                            contactRepository.addListContact(it)
                        }
                    }

                } else {
                    // handle error
                }
            }

            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
                // handle error
            }
        })
    }


    fun getContactById(id: Int) {
        viewModelScope.launch {
            val curContactCall = contactService.getContactById(id)
            curContactCall.enqueue(object : Callback<Contact> {
                override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _curContact.postValue(it)
                        }
                    } else {
                        // handle error
                    }
                }

                override fun onFailure(call: Call<Contact>, t: Throwable) {
                    // handle error
                }
            })
        }
    }

    fun createMessage(message: Message) {
        viewModelScope.launch {
            val createMessageCall = messageService.createMessage(message)
            createMessageCall.enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful) {
                        // handle success
                        viewModelScope.launch {
                            messageRepository.addMessage(message)

                            _curConversation.postValue(
                                messageRepository.getMessageByContactId(message.idReceiveContact)
                            )
                        }
                    } else {
                        // handle error
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    // handle error
                }
            })
        }
    }

    fun getConversationByIdContact(id: Int) {
        viewModelScope.launch {
            val messages = messageRepository.getMessageByContactId(id)
            _curConversation.postValue(messages)
        }
    }

    private fun setListConversation() {
        viewModelScope.launch {
            val messages = messageRepository.getMessageByContactId(idAccount)

            // group messages by contact id
            val groupMessages = messages.groupBy {
                if (it.idSendContact == idAccount) // if user is sender then group by idReceive
                    it.idReceiveContact
                else    // if user is receiver then group by idSend
                    it.idSendContact
            }.toList()
                .sortedByDescending { conversation ->
                    conversation.second.maxByOrNull { message -> message.time }?.time
                }// convert map to list pair

            val conversations = mutableListOf<Pair<Contact, List<Message>>>()

            groupMessages.forEach { (idContact, messages) ->
                val contact = contactRepository.getContactById(idContact)
                if (contact != null) {
                    conversations.add(contact to messages.sortedBy { it.time })
                }
            }
            _conversationList.postValue(conversations)
        }
    }

    private fun fetchMessageFromIdContact() {
        val getMessageFromIdContactCall = messageService.getMessageFromIdContact(idAccount)
        getMessageFromIdContactCall.enqueue(object : Callback<List<Message>> {
            override fun onResponse(
                call: Call<List<Message>>,
                response: Response<List<Message>>
            ) {
                if (response.isSuccessful) {
                    Log.d("MainViewModel", "fetchMessageFromIdContact: ${response.body()}")
                    val messages = response.body()
                    messages?.let {
                        viewModelScope.launch {
                            messageRepository.addListMessage(it)
                        }
                    }
                } else {
                    // handle error
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                // handle error
            }
        })
    }
}

class MainViewModelFactory(
    private val contactRepository: ContactRepository,
    private val messageRepository: MessageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                contactRepository, messageRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}