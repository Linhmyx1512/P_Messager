package dev.proptit.messenger.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.data.chat.Contact
import dev.proptit.messenger.databinding.ItemOnlineContactBinding

class OnlineContactAdapter(
    private val contacts: MutableList<Contact>
) :
    RecyclerView.Adapter<OnlineContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val binding: ItemOnlineContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.apply {
                Glide.with(binding.root).load(contact.imageId).into(binding.avtContact)
                nameContact.text = contact.name
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newContacts: List<Contact>) {
        contacts.clear()
        contacts.addAll(newContacts)
        notifyDataSetChanged()
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