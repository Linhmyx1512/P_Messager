package dev.proptit.messenger.ui.account

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dev.proptit.messenger.R
import dev.proptit.messenger.api.ApiClient
import dev.proptit.messenger.api.LoginData
import dev.proptit.messenger.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextChangeListener()
        setOnclick()
    }

    private fun setOnclick() {
        binding.apply {

            // handle click to register
            btnRegister.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment
                )
            }

            // handle click to login
            btnLogin.setOnClickListener {
                binding.apply {
                    val username = usernameInput.text.toString()
                    if (username.isEmpty()) {
                        handleError(
                            usernameInputLayout,
                            usernameInput,
                            getString(R.string.username_empty)
                        )
                        return@setOnClickListener
                    }
                    val password = passwordInput.text.toString()
                    if (password.isEmpty()) {
                        handleError(
                            passwordInputLayout,
                            passwordInput,
                            getString(R.string.password_empty)
                        )
                        return@setOnClickListener
                    }
                    handleLogin(username, password)
                }
            }
        }
    }

    private fun handleLogin(username: String, password: String) {
        val loginCall : Call<Int> = ApiClient.apiService.login(LoginData( username, password))
        loginCall.enqueue(object: Callback<Int>{
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    val bundle = Bundle()
                    bundle.putInt("id", result!!)
                    findNavController().navigate(R.id.action_loginFragment_to_chatsFragment, bundle)
                } else {
                    binding.tvDescription.text = getString(R.string.not_exist_account)
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                binding.tvDescription.text = getString(R.string.login_error)
            }
        })
    }

    private fun setupTextChangeListener() {
        binding.apply {
            usernameInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                clearError(binding.usernameInputLayout, binding.usernameInput)
            })
            passwordInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                clearError(binding.passwordInputLayout, binding.passwordInput)
            })
        }
    }


    private fun clearError(textInputLayout: TextInputLayout, textInputEditText: TextInputEditText) {
        textInputLayout.setBackgroundResource(R.drawable.bg_rounded_edt)
        textInputEditText.setHintTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.hint_text_color
                )
            )
        )
        binding.tvDescription.text = ""
    }

    private fun handleError(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
        description: String
    ) {
        textInputEditText.setHintTextColor(ColorStateList.valueOf(Color.RED))
        textInputLayout.setBackgroundResource(R.drawable.bg_error_rounded_edt)
        binding.tvDescription.apply {
            text = description
            setTextColor(Color.RED)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}