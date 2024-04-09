package dev.proptit.messenger.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.ItemChatBinding
import dev.proptit.messenger.domain.model.Contact

class ChatsAdapter :
    ListAdapter<Contact, ChatsAdapter.ChatViewHolder>(ChatDiffUtil()) {

    var onContactClick: (idReceive: Int) -> Unit = {}

    inner class ChatViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.apply {
                name.text = contact.name
                Glide.with(binding.root).load(contact.avatar)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_load_error)
                    .into(binding.imageAvatar)

                root.setOnClickListener {
                    onContactClick(contact.id)
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


    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChatDiffUtil : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

}