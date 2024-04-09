package dev.proptit.messenger.presentation.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.proptit.messenger.data.remote.dto.ContactLoginInputDto
import dev.proptit.messenger.data.remote.dto.ContactRegisterInputDto
import dev.proptit.messenger.data.remote.service.ContactService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val contactService: ContactService
) : ViewModel() {
    fun login(
        username: String,
        password: String,
        onSuccess: (Int) -> Unit,
        onError: () -> Unit
    ) {
        val contact = ContactLoginInputDto(username, password)
        val call = contactService.login(contact)
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val id = response.body()
                    if (id != null) {
                        onSuccess(id)
                    } else {
                        onError()
                    }
                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                onError()
            }
        })
    }


    fun register(
        name: String,
        username: String,
        password: String,
        onSuccess: (Int) -> Unit,
        onError: () -> Unit
    ){
        val contact = ContactRegisterInputDto(name, "", username, password)
        val call = contactService.register(contact)
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val id = response.body()
                    if (id != null) {
                        onSuccess(id)
                    } else {
                        onError()
                    }
                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                onError()
            }
        })
    }

}