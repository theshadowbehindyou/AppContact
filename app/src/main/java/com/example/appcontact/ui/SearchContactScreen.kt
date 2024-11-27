package com.example.appcontact.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.appcontact.utils.openDialer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContactScreen(viewModel: ContactViewModel, navController: NavHostController) {
    val allContacts = remember { mutableStateOf<List<ContactEntity>>(emptyList()) }
    val filteredContacts = remember { mutableStateOf<List<ContactEntity>>(emptyList()) }
    val searchQuery = remember { mutableStateOf("") }

    // Lấy danh sách liên hệ từ ViewModel
    LaunchedEffect(Unit) {
        allContacts.value = viewModel.getAllContacts()
        filteredContacts.value = allContacts.value // Ban đầu hiển thị tất cả liên hệ
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchQuery.value,
                        onValueChange = { query ->
                            searchQuery.value = query
                            filteredContacts.value = allContacts.value.filter {
                                it.name.contains(query, ignoreCase = true) // Lọc không phân biệt hoa/thường
                            }
                        },
                        placeholder = { Text("Search...") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(filteredContacts.value) { contact ->
                ContactRow(contact, onClick = {
                    navController.navigate("contact_detail/${contact.id}")
                })
            }
        }
    }
}
@Composable
fun ContactRow(contact: ContactEntity, onClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Khoảng cách giữa các card
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp) // Đổ bóng cho card
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Padding bên trong card
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = contact.avatar,
                    error = painterResource(id = R.drawable.placeholder_avatar),
                    fallback = painterResource(id = R.drawable.placeholder_avatar)
                ),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(64.dp) // Kích thước avatar
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp)) // Khoảng cách giữa avatar và văn bản
            Text(
                text = contact.name,
                style = MaterialTheme.typography.titleLarge, // Font chữ lớn
                modifier = Modifier.weight(1f)
            )
//            IconButton(onClick = {
//                openDialer(context = context, phone = contact.phone)
//            }) {
//                Icon(imageVector = Icons.Default.Call, contentDescription = "Call")
//            }
        }
    }
}


