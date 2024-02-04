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
import androidx.navigation.Navigation
import dev.proptit.messenger.R
import dev.proptit.messenger.data.chat.ContactRepository
import dev.proptit.messenger.data.message.MessageRepository
import dev.proptit.messenger.databinding.FragmentChatsBinding
import dev.proptit.messenger.ui.ChatsViewModel
import dev.proptit.messenger.ui.ChatsViewModelFactory
import dev.proptit.messenger.ui.adapters.ChatsAdapter
import dev.proptit.messenger.ui.adapters.OnlineContactAdapter
import kotlinx.coroutines.launch

class ChatsFragment : Fragment() {

    private lateinit var chatsAdapter: ChatsAdapter
    private lateinit var onlineContactAdapter: OnlineContactAdapter
    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private val chatsViewModel: ChatsViewModel by viewModels(
        factoryProducer = {
            ChatsViewModelFactory(
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


        chatsAdapter = ChatsAdapter (mutableListOf()){
            Navigation.findNavController(view)
                .navigate(R.id.action_chatsFragment_to_chatFragment)
        }
        binding.recyclerView.adapter = chatsAdapter

//        onlineContactAdapter = OnlineContactAdapter(contacts)
//        binding.recyclerViewOlContact = onlineContactAdapter

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatsViewModel.setupData()
                chatsViewModel.conversationList.observe(viewLifecycleOwner){
                    chatsAdapter.submitList(it.map {chatData ->
                        chatData.first to chatData.second.last()
                    })
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}