package dev.proptit.messenger.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.R
import dev.proptit.messenger.data.Chat
import dev.proptit.messenger.databinding.ItemChatBinding

class ChatsAdapter(private val chats: List<Chat>) :
    RecyclerView.Adapter<ChatsAdapter.ChatViewHolder>() {


    class ChatViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.apply {
                name.text = chat.name
                message.text = chat.message
                time.text = chat.time
                // set image use glide
                imageAvatar.setImageResource(chat.imageId)
                if (chat.isSent) {
                    iconCheckSent.setImageResource(R.drawable.icon_sent)
                } else {
                    iconCheckSent.setImageResource(R.drawable.icon_unsent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chats[position])
    }
}