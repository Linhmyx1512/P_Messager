package dev.proptit.messenger.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.R
import dev.proptit.messenger.data.contact.Contact
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.databinding.ItemChatBinding

class ChatsAdapter(
    private val conversations: MutableList<Pair<Contact, Message>>,
    private val onItemClick: (idContact: Int) -> Unit
) : RecyclerView.Adapter<ChatsAdapter.ChatViewHolder>() {

   inner class ChatViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact, lastMessage: Message?, onItemClick: (idReceive: Int) -> Unit) {
            binding.apply {

                name.text = contact.name
                message.text = lastMessage?.message ?: " "
                time.text = lastMessage?.time.toString()
                // set image use glide
                Glide.with(binding.root).load(contact.avatar).into(binding.imageAvatar)

                Glide.with(binding.root).load(R.drawable.icon_sent)
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

    override fun getItemCount(): Int = conversations.size
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatData =  conversations[position]
        holder.bind(chatData.first, chatData.second, onItemClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newConversations: List<Pair<Contact, Message>>) {
        conversations.clear()
        conversations.addAll(newConversations)
        notifyDataSetChanged()
    }
}