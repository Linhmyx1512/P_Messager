package dev.proptit.messenger.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.proptit.messenger.R
import dev.proptit.messenger.data.chat.Contact
import dev.proptit.messenger.data.chat.ContactRepository
import dev.proptit.messenger.data.message.MessageRepository
import dev.proptit.messenger.databinding.FragmentChatBinding
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
            Log.d("ChatFragment", "observeData: ${it.size}")
        }
    }

    private fun setupBottomNav() {
        bottomNav= requireActivity().findViewById(R.id.bottom_nav)
        bottomNav.visibility = View.GONE
    }

    private fun setupOnclick() {
        binding.iconBack.setOnClickListener{
            Navigation.findNavController(requireView()).navigate(R.id.action_chatFragment_to_chatsFragment)
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