package dev.proptit.messenger.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.data.Chat
import dev.proptit.messenger.databinding.ItemOnlineContactBinding

class OnlineContactAdapter(private val contacts: List<Chat>) :
    RecyclerView.Adapter<OnlineContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(private val binding: ItemOnlineContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.apply {
                avtContact.setImageResource(chat.imageId)
                nameContact.text = chat.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemOnlineContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }
}