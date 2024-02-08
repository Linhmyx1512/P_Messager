package dev.proptit.messenger.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.databinding.ItemMessageBinding

class MessageAdapter(
    private val messages: MutableList<Message>
) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.apply {
                if (message.idSend == 0) {
                    myMessageContainer.visibility = View.VISIBLE
                    contactMessage.visibility = View.GONE
                    myMessage.text = message.message
                } else {
                    myMessageContainer.visibility = View.GONE
                    contactMessage.visibility = View.VISIBLE
                    contactMessage.text = message.message
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newMessages : List<Message>){
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
        Log.d("MessageAdapter", "submitList: ${messages.size}")
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

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }
}