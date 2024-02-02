package dev.proptit.messenger.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.databinding.ItemMessageBinding

class MessageAdapter :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){

    inner class MessageViewHolder(private val binding:ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message){
            if (message.idSend == 0) {
                binding.apply {
                    myMessageContainer.visibility = View.VISIBLE
                    contactMessage.visibility = View.GONE
                    myMessage.text = message.message
                }
            } else {
                binding.apply {
                    myMessageContainer.visibility = View.GONE
                    contactMessage.visibility = View.VISIBLE
                    contactMessage.text = message.message
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}