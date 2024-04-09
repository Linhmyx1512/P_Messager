package dev.proptit.messenger.presentation.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.FragmentLoginBinding
import dev.proptit.messenger.presentation.MainActivity
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.setup.PrefManager
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
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

                    loginViewModel.login(
                        username,
                        password,
                        onSuccess = {
                            prefManager.put(Keys.MY_ACCOUNT_ID, it)
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                        },
                        onError = {
                            binding.tvDescription.text = getString(R.string.not_exist_account)
                        }
                    )
                }
            }
        }
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
        binding.tvDescription.text = description

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}