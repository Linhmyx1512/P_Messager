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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.FragmentRegisterBinding
import dev.proptit.messenger.presentation.MainActivity
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.setup.PrefManager
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        setupTextChangeListener()
        handleOnclickRegister()
    }

    private fun handleOnclickRegister() {
        binding.btnRegister.setOnClickListener {
            binding.apply {
                val name = nameInput.text.toString()
                if (name.isEmpty()) {
                    handleError(nameInputLayout, nameInput, getString(R.string.name_empty))
                    return@setOnClickListener
                }
                val userName = usernameInput.text.toString()
                if (userName.isEmpty()) {
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
                } else if (password.length < 6) {
                    handleError(
                        passwordInputLayout,
                        passwordInput,
                        getString(R.string.short_password)
                    )
                    return@setOnClickListener
                }
                val confirmPassword = cfPasswordInput.text.toString()
                if (confirmPassword.isEmpty()) {
                    handleError(
                        cfPasswordInputLayout,
                        cfPasswordInput,
                        getString(R.string.cf_password_empty)
                    )
                    return@setOnClickListener
                } else if (confirmPassword != password) {
                    handleError(
                        cfPasswordInputLayout,
                        cfPasswordInput,
                        getString(R.string.cf_password_error)
                    )
                    return@setOnClickListener
                }
                loginViewModel.register(name, userName, password, {
                    prefManager.put(Keys.MY_ACCOUNT_ID, it)
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                }, {
                    binding.tvDescription.apply {
                        text = getString(R.string.register_failed)
                        setTextColor(Color.RED)
                    }
                })
            }
        }
    }


    private fun handleError(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
        description: String
    ) {
        textInputEditText.apply {
            error = ""
            setHintTextColor(ColorStateList.valueOf(Color.RED))
        }
        textInputLayout.setBackgroundResource(R.drawable.bg_error_rounded_edt)
        binding.tvDescription.apply {
            text = description
            setTextColor(Color.RED)
        }
    }

    private fun setupTextChangeListener() {
        binding.apply {
            nameInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                clearError(binding.nameInputLayout, binding.nameInput)
            })
            usernameInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                clearError(binding.usernameInputLayout, binding.usernameInput)
            })
            passwordInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                clearError(binding.passwordInputLayout, binding.passwordInput)
            })
            cfPasswordInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                clearError(binding.cfPasswordInputLayout, binding.cfPasswordInput)
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
        binding.tvDescription.apply {
            text = getText(R.string.password_description)
            setTextColor(Color.BLACK)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}