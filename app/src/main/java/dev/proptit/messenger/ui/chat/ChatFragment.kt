package dev.proptit.messenger.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.proptit.messenger.R
import dev.proptit.messenger.data.contact.Contact
import dev.proptit.messenger.data.contact.ContactRepository
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.data.message.MessageRepository
import dev.proptit.messenger.databinding.FragmentChatBinding
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.ui.MyViewModel
import dev.proptit.messenger.ui.MyViewModelFactory
import dev.proptit.messenger.ui.adapters.MessageAdapter

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNav: BottomNavigationView
    private var idContact: Int = -1
    private lateinit var messageAdapter: MessageAdapter

    private val chatViewModel: MyViewModel by viewModels(
        factoryProducer = {
            MyViewModelFactory(
                ContactRepository(),
                MessageRepository()
            )
        }
    )

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
        observeData()
        setupBottomNav()
        setupAdapter()
        setupOnclick()
    }



    private fun setupAdapter() {
        messageAdapter = MessageAdapter(mutableListOf())
        binding.chatRecyclerView.adapter = messageAdapter
    }

    private fun observeData() {

        chatViewModel.apply{
            getContactById(idContact)
            getConversationByIdContact(idContact)
        }

        chatViewModel.curContact.observe(viewLifecycleOwner) {
            showContactData(it)
        }

        chatViewModel.curConversation.observe(viewLifecycleOwner) {
            messageAdapter.submitList(it)
        }
    }

    private fun setupBottomNav() {
        bottomNav= requireActivity().findViewById(R.id.bottom_nav)
        bottomNav.visibility = View.GONE
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
                val message = Message(0, Keys.MY_ID, idContact, edtMessage.text.toString())
                chatViewModel.addNewMessage(message)
                edtMessage.text?.clear()
            }
        }
    }

    private fun showContactData(contact: Contact) {
        binding.name.text = contact.name
        Glide.with(binding.root).load(contact.imageId).into(binding.imageAvatar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNav.visibility = View.VISIBLE
        _binding = null
    }
}