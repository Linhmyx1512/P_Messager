package dev.proptit.messenger.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.data.contact.Contact
import dev.proptit.messenger.databinding.ItemContactBinding

class ContactAdapter(
    private val contacts: MutableList<Contact>,
    private val onItemClick: (idContact: Int) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(contact: Contact) {
            Glide.with(binding.root).load(contact.avatar).into(binding.imageAvatar)
            binding.name.text = contact.name
            binding.root.setOnClickListener {
                onItemClick(contact.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactBinding.inflate(
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

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newContacts: List<Contact>) {
        contacts.clear()
        contacts.addAll(newContacts)
        notifyDataSetChanged()
    }

}