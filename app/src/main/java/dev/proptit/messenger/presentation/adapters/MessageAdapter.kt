package dev.proptit.messenger.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.databinding.ItemMessageBinding
import dev.proptit.messenger.domain.model.Message

class MessageAdapter(private val myId: Int) : ListAdapter<Message,MessageAdapter.MessageViewHolder>(MessageDiffUtil()) {

    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.apply {
                if (message.idSendContact == myId) {
                    myMessageContainer.visibility = View.VISIBLE
                    contactMessageContainer.visibility = View.GONE
                    myMessage.text = message.message
                } else {
                    myMessageContainer.visibility = View.GONE
                    contactMessageContainer.visibility = View.VISIBLE
                    contactMessage.text = message.message
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageDiffUtil : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}