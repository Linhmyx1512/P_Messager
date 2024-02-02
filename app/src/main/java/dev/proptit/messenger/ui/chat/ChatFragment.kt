package dev.proptit.messenger.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNav: BottomNavigationView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNav= requireActivity().findViewById(R.id.bottom_nav)
        bottomNav.visibility = View.GONE
        binding.iconBack.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_chatFragment_to_chatsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNav.visibility = View.VISIBLE
        _binding = null
    }
}