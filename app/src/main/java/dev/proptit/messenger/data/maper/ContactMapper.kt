package dev.proptit.messenger.data.maper

import dev.proptit.messenger.data.local.entity.ContactEntity
import dev.proptit.messenger.data.remote.dto.ContactOutputDto
import dev.proptit.messenger.domain.model.Contact

object ContactMapper {
    fun getListContactEntityFromListContactOutputDto(contactOutputDto: List<ContactOutputDto>?): List<ContactEntity> {
        val contacts = mutableListOf<ContactEntity>()
        contactOutputDto?.forEach {
            contacts.add(
                ContactEntity(
                    it.id,
                    it.name,
                    it.avatar,
                )
            )
        }
        return contacts
    }

    fun getContactEntityFromContact( contact: Contact): ContactEntity {
        return ContactEntity(
            contact.id,
            contact.name,
            contact.avatar,
        )
    }

    fun getContactFromContactEntity(contactEntity: ContactEntity): Contact {
        return Contact(
            contactEntity.id,
            contactEntity.name,
            contactEntity.avatar,
        )
    }
}