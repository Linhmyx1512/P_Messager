package dev.proptit.messenger.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.proptit.messenger.data.remote.dto.MessageCreateInputDto
import dev.proptit.messenger.data.remote.dto.MessageOutputDto
import dev.proptit.messenger.data.remote.service.MessageService
import dev.proptit.messenger.domain.model.Contact
import dev.proptit.messenger.domain.model.Message
import dev.proptit.messenger.domain.repository.ContactRepository
import dev.proptit.messenger.domain.repository.MessageRepository
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.setup.PrefManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val messageRepository: MessageRepository,
    private val messageService: MessageService,
    private val prefManager: PrefManager
) : ViewModel() {

    private val _allContacts = MutableStateFlow<List<Contact>>(listOf())
    val allContacts = _allContacts.asStateFlow()

    private val _people = MutableStateFlow<List<Contact>>(listOf())
    val people = _people.asStateFlow()

    private val _contact = MutableStateFlow(Contact(0, "", ""))
    val contact = _contact.asStateFlow()

    private val _allMessage = MutableStateFlow<List<Message>>(listOf())
    val allMessages = _allMessage.asStateFlow()

    private var job: Job? = null

    init {
        viewModelScope.launch {
            contactRepository.getContactByUserId(prefManager.get(Keys.MY_ACCOUNT_ID, -1))
                .collect { list ->
                    _allContacts.update { list }
                }
        }

        viewModelScope.launch {
            contactRepository.getAllContact()
                .collect { list ->
                    _people.update {
                        list.filter {
                            it.id != prefManager.get(
                                Keys.MY_ACCOUNT_ID,
                                -1
                            )
                        }
                    }
                }
        }
    }

    fun sendMessage(message: String, idSendContact: Int, idReceiveContact: Int) {
        val call = messageService.createMessage(
            MessageCreateInputDto(
                message,
                idSendContact,
                idReceiveContact
            )
        )

        call.enqueue(object : Callback<MessageOutputDto> {
            override fun onResponse(
                call: Call<MessageOutputDto>,
                response: Response<MessageOutputDto>
            ) {
                if (response.isSuccessful) {
                    Log.d("TAG", "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<MessageOutputDto>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }
        })
    }


    fun setContact(myId: Int, id: Int) {
        job?.cancel()
        viewModelScope.launch {
            _contact.update {
                getContactById(id)
            }
        }

        _allMessage.update { listOf() }

        job = viewModelScope.launch {
            messageRepository.getMessageByContactId(myId, id).collect { list ->
                _allMessage.update { list }
            }
        }
    }

    private fun getContactById(id: Int): Contact {
        return _people.value.find { it.id == id } ?: Contact(0, "", "")
    }
}
