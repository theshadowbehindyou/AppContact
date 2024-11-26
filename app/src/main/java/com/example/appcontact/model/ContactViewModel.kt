package com.example.appcontact.model

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {
    fun insertContact(contact: ContactEntity) {
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }

    fun updateContact(contact: ContactEntity) {
        viewModelScope.launch {
            repository.updateContact(contact)
        }
    }

    fun deleteContact(contact: ContactEntity) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }

    suspend fun getContactById(contactId: Int): ContactEntity? {
        return repository.getContactById(contactId)
    }

    suspend fun getAllContacts(): List<ContactEntity> {
        return repository.getAllContacts()
    }


}
