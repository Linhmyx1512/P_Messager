package dev.proptit.messenger.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.R
import dev.proptit.messenger.data.chat.Contact
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.databinding.ItemChatBinding

class ChatsAdapter(
    private val onItemClick: (idReceive: Int) -> Unit
) : RecyclerView.Adapter<ChatsAdapter.ChatViewHolder>() {

    private var conversationList = listOf<Contact>()
    private var messageList = listOf<Message>()
   inner class ChatViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.apply {
                name.text = contact.name
//                message.text = .message
                // set image use glide
                Glide.with(binding.root).load(contact.imageId).into(binding.imageAvatar)
                Glide.with(binding.root).load(if (contact.isSent) R.drawable.icon_sent else R.drawable.icon_unsent)
                    .into(binding.iconCheckSent)

                root.setOnClickListener {
                    onItemClick(contact.id)
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

    override fun getItemCount(): Int = conversationList.size
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(conversationList[position])
    }
}