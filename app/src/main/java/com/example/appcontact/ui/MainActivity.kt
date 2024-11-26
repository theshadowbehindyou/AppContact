package com.example.appcontact.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.appcontact.model.ContactViewModel

import com.example.appcontact.model.ContactDatabase
import com.example.appcontact.model.ContactRepository
import com.example.appcontact.model.ContactViewModelFactory
import com.example.appcontact.ui.theme.ContactsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tạo instance của cơ sở dữ liệu
        val database = ContactDatabase.getDatabase(this)
        val repository = ContactRepository(database.contactDao())

        // Tạo instance của ViewModel
        val contactViewModel = ViewModelProvider(
            this,
            ContactViewModelFactory(repository)
        )[ContactViewModel::class.java]

        setContent {
            ContactsAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainApp(viewModel = contactViewModel) // Truyền ViewModel vào MainApp
                }
            }
        }
    }
}