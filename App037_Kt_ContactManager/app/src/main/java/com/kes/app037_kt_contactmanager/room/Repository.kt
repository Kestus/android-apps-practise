package com.kes.app037_kt_contactmanager.room

class Repository(
    private val dao: ContactDAO
) {

    val contacts = dao.getAllContacts()

    suspend fun insert(contact: Contact): Long = dao.insertContact(contact)

    suspend fun delete(contact: Contact) = dao.deleteContact(contact)

    suspend fun update(contact: Contact) = dao.updateContact(contact)

    suspend fun deleteAll() = dao.deleteAll()

}