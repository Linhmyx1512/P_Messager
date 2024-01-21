package dev.proptit.messenger.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.R
import dev.proptit.messenger.data.Chat

class OnlineContactAdapter(private val contacts: List<Chat>) :
    RecyclerView.Adapter<OnlineContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chat: Chat) {
            itemView.apply {
                findViewById<ImageView>(R.id.avt_contact).setImageResource(chat.imageId)
                findViewById<TextView>(R.id.name_contact).text = chat.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = View.inflate(parent.context, R.layout.item_online_contact, null)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }
}