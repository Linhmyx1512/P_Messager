package dev.proptit.messenger.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.FragmentChatBinding
import dev.proptit.messenger.presentation.MainViewModel
import dev.proptit.messenger.presentation.adapters.MessageAdapter
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.setup.PrefManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {
    @Inject
    lateinit var prefManager: PrefManager

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private var messageAdapter: MessageAdapter? = null

    private val chatViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showContactData()
        setupAdapter()
        observeData()
        setupOnclick()
    }


    private fun setupAdapter() {
        messageAdapter = MessageAdapter(prefManager.get(Keys.MY_ACCOUNT_ID, -1))
        binding.chatRecyclerView.adapter = messageAdapter
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatViewModel.allMessages.collect {
                    messageAdapter?.submitList(it)
                }
            }
        }
    }

    private fun setupOnclick() {
        binding.apply {
            // back to chats fragment
            iconBack.setOnClickListener {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_chatFragment_to_chatsFragment)
            }

            // on text change
            edtMessage.addTextChangedListener {
                if (it.isNullOrBlank()) {
                    actionsContainer.visibility = View.VISIBLE
                    btnExpand.visibility = View.GONE
                    btnLike.visibility = View.VISIBLE
                    btnSend.visibility = View.GONE
                } else {
                    actionsContainer.visibility = View.GONE
                    btnExpand.visibility = View.VISIBLE
                    btnLike.visibility = View.GONE
                    btnSend.visibility = View.VISIBLE
                }
            }
            // send message
            btnSend.setOnClickListener {
                chatViewModel.sendMessage(
                    message = edtMessage.text.toString(),
                    idSendContact = prefManager.get(Keys.MY_ACCOUNT_ID, -1),
                    idReceiveContact = chatViewModel.contact.value.id
                )
//                Log.d("ChatFragment", "message: $message")
                edtMessage.text?.clear()
            }
        }
    }

    private fun showContactData() {
        val contact = chatViewModel.contact.value
        binding.name.text = contact.name
        Glide.with(binding.root).load(contact.avatar)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_load_error)
            .into(binding.imageAvatar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}