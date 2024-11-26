package com.example.appcontact.model

class ContactRepository(private val contactDao: ContactDao) {
    suspend fun insertContact(contact: ContactEntity) = contactDao.insertContact(contact)
    suspend fun updateContact(contact: ContactEntity) = contactDao.updateContact(contact)
    suspend fun deleteContact(contact: ContactEntity) = contactDao.deleteContact(contact)
    suspend fun getContactById(contactId: Int): ContactEntity? = contactDao.getContactById(contactId)
    suspend fun getAllContacts(): List<ContactEntity> = contactDao.getAllContacts()
}
