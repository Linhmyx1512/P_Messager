package dev.proptit.messenger.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.ItemOnlineContactBinding
import dev.proptit.messenger.domain.model.Contact

class OnlineContactAdapter :
    ListAdapter<Contact, OnlineContactAdapter.ContactViewHolder>(OnlineContactDiffUtil()) {

    var onItemClick: (idReceive: Int) -> Unit = {}

    inner class ContactViewHolder(private val binding: ItemOnlineContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.apply {
                Glide.with(binding.root).load(contact.avatar)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_load_error)
                    .into(binding.avtContact)

                nameContact.text = contact.name
                root.setOnClickListener {
                    onItemClick(contact.id)
                }
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

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OnlineContactDiffUtil : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }
}