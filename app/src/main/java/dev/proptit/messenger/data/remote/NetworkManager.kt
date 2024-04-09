package dev.proptit.messenger.data.remote

import dev.proptit.messenger.data.maper.ContactMapper
import dev.proptit.messenger.data.maper.MessageMapper
import dev.proptit.messenger.data.remote.dto.ContactOutputDto
import dev.proptit.messenger.data.remote.dto.MessageOutputDto
import dev.proptit.messenger.data.remote.service.ContactService
import dev.proptit.messenger.data.remote.service.MessageService
import dev.proptit.messenger.domain.repository.ContactRepository
import dev.proptit.messenger.domain.repository.MessageRepository
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.setup.PrefManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NetworkManager @Inject constructor(
    private val contactService: ContactService,
    private val contactRepository: ContactRepository,
    private val messageService: MessageService,
    private val messageRepository: MessageRepository,
    private val contactMapper: ContactMapper,
    private val messageMapper: MessageMapper,
    private val prefManager: PrefManager
) {

    private var job: Job? = null

    fun register() {
        job = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(2000)
                updateContact()
                updateMessage()
            }
        }
    }

    private fun updateContact() {
        val call =
            contactService.getAllContact()
        call.enqueue(object : Callback<List<ContactOutputDto>> {
            override fun onResponse(
                call: Call<List<ContactOutputDto>>,
                response: Response<List<ContactOutputDto>>
            ) {
                if (response.isSuccessful) {
                    val contacts = response.body()
                    CoroutineScope(Dispatchers.IO).launch {
                        contactRepository.addListContact(
                            contactMapper.getListContactEntityFromListContactOutputDto(contacts)
                        )
                    }
                }
            }

            override fun onFailure(call: Call<List<ContactOutputDto>>, t: Throwable) {
            }
        })
    }

    private fun updateMessage() {
        val call =
            messageService.findAllMessageWithContactId(prefManager.get(Keys.MY_ACCOUNT_ID, -1))
        call.enqueue(object : Callback<List<MessageOutputDto>> {
            override fun onResponse(
                call: Call<List<MessageOutputDto>>,
                response: Response<List<MessageOutputDto>>
            ) {
                if (response.isSuccessful) {
                    val messages = response.body()
                    CoroutineScope(Dispatchers.IO).launch {
                        messageRepository.addListMessage(
                            messageMapper.getListMessageEntityFromListMessageOutputDto(messages)
                        )
                    }
                }
            }

            override fun onFailure(call: Call<List<MessageOutputDto>>, t: Throwable) {
            }

        })
    }

    fun unregister() {
        job?.cancel()
        job = null
    }

}