package dev.proptit.messenger.ui
import android.util.Log
import androidx.lifecycle.ViewModel
import dev.proptit.messenger.R
import dev.proptit.messenger.api.ApiClient
import dev.proptit.messenger.api.LoginData
import dev.proptit.messenger.api.RegisterData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val contactService = ApiClient.contactService

    fun handleLogin(username: String, password: String, onSuccess: (Int) -> Unit, onFailure: (String) -> Unit) {
        val loginCall : Call<Int> = contactService.login(LoginData( username, password))
        loginCall.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    onSuccess(result!!)
                } else {
                    onFailure(R.string .not_exist_account.toString())
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("LoginViewModel", t.message.toString())
                onFailure(t.message.toString())
            }
        })
    }


    fun handleRegister(name: String, userName: String, password: String, onSuccess: (Int) -> Unit, onFailure: (String) -> Unit) {
        val registerCall: Call<Int> =
            contactService.register(RegisterData(name, "", userName, password))
        registerCall.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    onSuccess(result!!)
                } else {
                    onFailure(response.errorBody()?.string().toString())
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                onFailure(t.message.toString())
            }

        })
    }

}