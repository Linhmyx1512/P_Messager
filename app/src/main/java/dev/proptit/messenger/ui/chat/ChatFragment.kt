package dev.proptit.messenger.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import dev.proptit.messenger.R
import dev.proptit.messenger.data.contact.Contact
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.databinding.FragmentChatBinding
import dev.proptit.messenger.ui.MainViewModel
import dev.proptit.messenger.ui.adapters.MessageAdapter

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private var idContact: Int = -1
    private lateinit var messageAdapter: MessageAdapter

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
        idContact = arguments?.getInt("idContact") ?: 0
        Log.d("ChatFragment", "idContact: $idContact")
        observeData()
        setupAdapter()
        setupOnclick()
    }



    private fun setupAdapter() {
        messageAdapter = MessageAdapter(mutableListOf(), chatViewModel.idAccount)
        binding.chatRecyclerView.adapter = messageAdapter
    }

    private fun observeData() {
        chatViewModel.getContactById(idContact)
        chatViewModel.getConversationByIdContact(idContact)

        chatViewModel.curContact.observe(viewLifecycleOwner) {
            showContactData(it)
        }

        chatViewModel.curConversation.observe(viewLifecycleOwner) {
            messageAdapter.submitList(it)
        }
    }

    private fun setupOnclick() {
        binding.apply {
            iconBack.setOnClickListener{
                Navigation.findNavController(requireView()).navigate(R.id.action_chatFragment_to_chatsFragment)
            }
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
            btnSend.setOnClickListener {
                val message = Message(
                    message = edtMessage.text.toString(),
                    idSendContact = chatViewModel.idAccount,
                    idReceiveContact = idContact
                )
                Log.d("ChatFragment", "message: $message")
                chatViewModel.createMessage(message)
                edtMessage.text?.clear()
            }
        }
    }

    private fun showContactData(contact: Contact) {
        binding.name.text = contact.name
        Glide.with(binding.root).load(contact.avatar).into(binding.imageAvatar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}