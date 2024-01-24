package dev.proptit.messenger.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.R
import dev.proptit.messenger.adapters.ChatsAdapter
import dev.proptit.messenger.adapters.OnlineContactAdapter
import dev.proptit.messenger.data.Chat
import dev.proptit.messenger.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {

    private lateinit var chats: List<Chat>
    private lateinit var chatsAdapter: ChatsAdapter
    private lateinit var contactAdapter: OnlineContactAdapter
    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chats = initData()
        chatsAdapter = ChatsAdapter(chats)
        contactAdapter = OnlineContactAdapter(chats)
        view.findViewById<RecyclerView>(R.id.recycler_view).adapter = chatsAdapter
        view.findViewById<RecyclerView>(R.id.recycler_view_ol_contact).adapter = contactAdapter
    }

    private fun initData(): List<Chat> {
        return listOf(
            Chat(0,"Martin Randolph", "You: What’s man!", "· 9:40 AM", R.drawable.image_ps1, false),
            Chat(1,"Andrew Parker", "You: Ok, thanks!", "· 9:25 AM ", R.drawable.image_ps2, true),
            Chat(2,"Karen Castillo", "You: What’s man!", "· 9:40 AM", R.drawable.image_ps3, false),
            Chat(3,"Maisy Humphrey", "You: Ok, thanks!", "· 9:40 AM", R.drawable.image_ps4, false),
            Chat(4,"Joshua Lawrence", "You: What’s man!", "· 9:40 AM", R.drawable.image_ps5, true),
            Chat(5,"Martin Randolph", "You: Ok, thanks!", "· 9:40 AM", R.drawable.image_ps1, false),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}