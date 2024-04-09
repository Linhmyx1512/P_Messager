package dev.proptit.messenger.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.ItemContactBinding
import dev.proptit.messenger.domain.model.Contact

class PeopleAdapter : ListAdapter<Contact, PeopleAdapter.ContactViewHolder>(ContactDiffUtil()) {

    var onContactClick: (idReceive: Int) -> Unit = {}

    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            Glide.with(binding.root).load(contact.avatar)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_load_error)
                .into(binding.imageAvatar)

            binding.name.text = contact.name
            binding.root.setOnClickListener {
                onContactClick(contact.id)
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

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContactDiffUtil : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

}