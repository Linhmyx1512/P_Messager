package dev.proptit.messenger.presentation.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.FragmentChatsBinding
import dev.proptit.messenger.presentation.MainViewModel
import dev.proptit.messenger.presentation.adapters.ChatsAdapter
import dev.proptit.messenger.presentation.adapters.OnlineContactAdapter
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.setup.PrefManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var prefManager: PrefManager

    private val chatsViewModel: MainViewModel by activityViewModels()

    private val chatsAdapter: ChatsAdapter by lazy {
        ChatsAdapter()
    }

    private val onlineContactAdapter: OnlineContactAdapter by lazy {
        OnlineContactAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observeData()

    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatsViewModel.allContacts.collect {
                    chatsAdapter.submitList(it)
                    onlineContactAdapter.submitList(it)
                }
            }
        }
    }


    private fun setupAdapter() {
        chatsAdapter.onContactClick = {
            chatsViewModel.setContact(prefManager.get(Keys.MY_ACCOUNT_ID, -1), it)
            findNavController().navigate(R.id.action_chatsFragment_to_chatFragment)
        }
        binding.recyclerView.adapter = chatsAdapter
        onlineContactAdapter.onItemClick = {
            chatsViewModel.setContact(prefManager.get(Keys.MY_ACCOUNT_ID, -1), it)
            findNavController().navigate(R.id.action_chatsFragment_to_chatFragment)
        }
        binding.recyclerViewOlContact.adapter = onlineContactAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}