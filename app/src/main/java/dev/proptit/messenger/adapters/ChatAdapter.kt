package dev.proptit.messenger.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.R
import dev.proptit.messenger.data.Chat

class ChatAdapter(private val chats: List<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {


    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chat: Chat) {
            itemView.apply {
                findViewById<TextView>(R.id.name).text = chat.name
                findViewById<TextView>(R.id.message).text = chat.message
                findViewById<TextView>(R.id.time).text = chat.time
                findViewById<ImageView>(R.id.image_avatar).setImageResource(chat.imageId)
                if (chat.isSent) {
                    findViewById<ImageView>(R.id.icon_check_sent).setImageResource(R.drawable.icon_sent)
                } else {
                    findViewById<ImageView>(R.id.icon_check_sent).setImageResource(R.drawable.icon_unsent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = View.inflate(parent.context, R.layout.item_chat, null)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chats[position])
    }
}