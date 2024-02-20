package dev.proptit.messenger.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dev.proptit.messenger.R
import dev.proptit.messenger.data.contact.ContactRepository
import dev.proptit.messenger.data.message.MessageRepository
import dev.proptit.messenger.databinding.FragmentChatsBinding
import dev.proptit.messenger.ui.MainViewModel
import dev.proptit.messenger.ui.MainViewModelFactory
import dev.proptit.messenger.ui.adapters.ChatsAdapter
import dev.proptit.messenger.ui.adapters.OnlineContactAdapter
import kotlinx.coroutines.launch

class ChatsFragment : Fragment() {

    private lateinit var chatsAdapter: ChatsAdapter
    private lateinit var onlineContactAdapter: OnlineContactAdapter
    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private val chatsViewModel: MainViewModel by viewModels(
        factoryProducer = {
            MainViewModelFactory(
                ContactRepository(),
                MessageRepository()
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupAdapter()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatsViewModel.conversationList.observe(viewLifecycleOwner){
                    chatsAdapter.submitList(it.map {chatData ->
                        chatData.first to chatData.second.last()
                    })
                }
                chatsViewModel.allContactList.observe(viewLifecycleOwner){
                    onlineContactAdapter.submitList(it)
                }
            }
        }
    }

    private fun handleOpenChat(idContact:Int){
        val bundle = Bundle()
        bundle.putInt("idContact", idContact)
        findNavController().navigate(
            R.id.action_chatsFragment_to_chatFragment,
            bundle
        )
    }

    private fun setupAdapter() {
        chatsAdapter = ChatsAdapter (mutableListOf()){
            handleOpenChat(it)
        }
        binding.recyclerView.adapter = chatsAdapter

        onlineContactAdapter = OnlineContactAdapter(mutableListOf()) {
            handleOpenChat(it)
        }
        binding.recyclerViewOlContact.adapter = onlineContactAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}