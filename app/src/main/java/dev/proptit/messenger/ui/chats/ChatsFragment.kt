package dev.proptit.messenger.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.R
import dev.proptit.messenger.adapters.ChatRecyclerViewAdapter
import dev.proptit.messenger.data.Chat

class ChatsFragment : Fragment() {

    private lateinit var chats: List<Chat>
    private lateinit var chatRecyclerViewAdapter: ChatRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chats = initData()
        chatRecyclerViewAdapter = ChatRecyclerViewAdapter(chats)
        view.findViewById<RecyclerView>(R.id.recycler_view).adapter = chatRecyclerViewAdapter
    }

    private fun initData(): List<Chat> {
        return listOf(
            Chat("Martin Randolph", "You: What’s man!", "· 9:40 AM", R.drawable.image_ps1, false),
            Chat("Andrew Parker", "You: Ok, thanks!", "· 9:25 AM ", R.drawable.image_ps2, true),
            Chat("Karen Castillo", "You: What’s man!", "· 9:40 AM", R.drawable.image_ps3, false),
            Chat("Maisy Humphrey", "You: Ok, thanks!", "· 9:40 AM", R.drawable.image_ps4, false),
            Chat("Joshua Lawrence", "You: What’s man!", "· 9:40 AM", R.drawable.image_ps5, true),
            Chat("Martin Randolph", "You: Ok, thanks!", "· 9:40 AM", R.drawable.image_ps1, false),
        )
    }

}