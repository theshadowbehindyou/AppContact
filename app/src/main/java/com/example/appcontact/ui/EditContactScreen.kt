package com.example.appcontact.ui
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.appcontact.model.ContactEntity
import com.example.appcontact.model.ContactViewModel
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.appcontact.utils.saveImageToInternalStorage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactScreen(
    contactId: Int,
    viewModel: ContactViewModel,
    navController: NavHostController,
    context: Context
) {
    val contact = remember { mutableStateOf<ContactEntity?>(null) }
    val avatar = remember { mutableStateOf(contact.value?.avatar ?: "") }

    // Lấy thông tin liên hệ từ ViewModel
    LaunchedEffect(contactId) {
        contact.value = viewModel.getContactById(contactId)
        avatar.value = contact.value?.avatar ?: ""
    }
    if (contact.value == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        return
    }

    val name = remember { mutableStateOf(contact.value?.name ?: "") }
    val phone = remember { mutableStateOf(contact.value?.phone ?: "") }
    val email = remember { mutableStateOf(contact.value?.email ?: "") }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
//                avatar.value = uri.toString() // Lưu URI của ảnh được chọn
                avatar.value = saveImageToInternalStorage(context, uri) ?: ""
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Contact") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            // Hiển thị ảnh (nếu có)
            if (avatar.value.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(avatar.value),
                    contentDescription = "Selected Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
            }
            // Nút bấm để thay đổi ảnh
            Button(
                onClick = {
                    pickImageLauncher.launch("image/*") // Mở màn hình chọn ảnh
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Change Picture")
            }
            // Trường nhập liệu
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = phone.value,
                onValueChange = { phone.value = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            // Nút bấm để lưu thông tin chỉnh sửa
            Button(
                onClick = {
                    contact.value?.let {
                        viewModel.updateContact(
                            it.copy(
                                name = name.value,
                                phone = phone.value,
                                email = email.value,
                                avatar = avatar.value
                            )
                        )
                    }
                    navController.popBackStack() // Quay lại màn hình chi tiết
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}