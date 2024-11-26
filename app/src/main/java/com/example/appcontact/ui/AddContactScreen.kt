package com.example.appcontact.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.appcontact.R
import com.example.appcontact.model.ContactEntity
import com.example.appcontact.model.ContactViewModel
import android.net.Uri
import com.example.appcontact.utils.saveImageToInternalStorage
import android.content.Context

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(navController: NavHostController,
                     viewModel: ContactViewModel,
                     context: Context
) {
    val name = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val avatar = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                // Gọi hàm saveImageToInternalStorage để lưu ảnh và lấy đường dẫn
                avatar.value = saveImageToInternalStorage(context, uri) ?: ""
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Contact") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Search, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = phone.value,
                onValueChange = { phone.value = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            if (avatar.value.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = avatar.value,
                        error = painterResource(id = R.drawable.placeholder_avatar),
                        fallback = painterResource(id = R.drawable.placeholder_avatar)
                    ),
                    contentDescription = "Selected Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Button(
                onClick = {
                    pickImageLauncher.launch("image/*") // Cho phép chọn ảnh
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Add Picture")
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (errorMessage.value.isNotEmpty()) {
                Text(
                    text = errorMessage.value,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (avatar.value.isEmpty()) {
                        errorMessage.value = "Avatar is empty. Using default avatar."
                        avatar.value = Uri.parse("android.resource://com.example.appcontact/drawable/placeholder_avatar").toString()
                    } else {
                        errorMessage.value = ""
                    }

                    viewModel.insertContact(
                        ContactEntity(
                            name = name.value,
                            phone = phone.value,
                            email = email.value,
                            avatar = avatar.value
                        )
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Contact")
            }
        }
    }
}
