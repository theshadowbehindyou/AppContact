package com.example.appcontact.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.appcontact.R
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.appcontact.model.ContactEntity
import com.example.appcontact.model.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactDetailScreen(viewModel: ContactViewModel, contactId: Int, navController: NavHostController) {
    val contact = remember { mutableStateOf<ContactEntity?>(null) }

    LaunchedEffect(contactId) {
        contact.value = viewModel.getContactById(contactId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(contact.value?.name ?: "Contact Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Menu */ }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("edit_contact/$contactId")
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Contact")
            }
        }
    ) { innerpadding ->
        // Hiển thị thông tin liên hệ
        contact.value?.let {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(innerpadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar
                Image(
                    painter = rememberAsyncImagePainter(
                        model = it.avatar,
                        error = painterResource(id = R.drawable.placeholder_avatar), // Ảnh mặc định khi xảy ra lỗi
                        fallback = painterResource(id = R.drawable.placeholder_avatar), // Fallback nếu không có avatar
                    ),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Name
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Phone Numbers
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = it.phone,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { /* Video Call action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.videocall), // Thay bằng icon Video Call của bạn
                                contentDescription = "Video Call"
                            )
                        }
                        IconButton(onClick = { /* Gọi điện thoại action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.message),
                                contentDescription = "Message"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = it.phone,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f).padding(start = 8.dp)
                        )
                        IconButton(onClick = { /* Video Call action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.videocall), // Thay bằng icon Video Call của bạn
                                contentDescription = "Video Call"
                            )
                        }
                        IconButton(onClick = { /* Gọi điện thoại action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.message),
                                contentDescription = "Message"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = it.email,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}






//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Avatar
//            Image(
//                painter = painterResource(R.drawable.placeholder_avatar), // Thay thế bằng ảnh trong res/drawable
//                contentDescription = "Avatar",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Name
//            Text(
//                text = contactName,
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Phone Numbers
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Row 1: Gọi điện
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Mobile: (650) 555-1367",
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.weight(1f)
//                )
//                IconButton(onClick = { /* Gọi điện action */ }) {
//                    Icon(
//                        imageVector = Icons.Default.Call,
//                        contentDescription = "Call"
//                    )
//                }
//                // Video Call bên phải
//                IconButton(onClick = { /* Video Call action */ }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.videocall), // Nạp từ drawable
//                        contentDescription = "Video Call"
//                    )
//                }
//                // Message bên phải
//                IconButton(onClick = { /* Message action */ }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.message),
//                        contentDescription = "Message"
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Row 2: Số điện thoại với Video Call và Message
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Số điện thoại bên trái
//                Text(
//                    text = "Home: (650) 555-1367",
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.weight(1f)
//                )
//                // Video Call bên phải
//                IconButton(onClick = { /* Video Call action */ }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.videocall), // Nạp từ drawable
//                        contentDescription = "Video Call"
//                    )
//                }
//                // Message bên phải
//                IconButton(onClick = { /* Message action */ }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.message),
//                        contentDescription = "Message"
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Row 3: Email
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Email icon bên trái
//                IconButton(onClick = { /* Email action */ }) {
//                    Icon(
//                        imageVector = Icons.Default.Email,
//                        contentDescription = "Email"
//                    )
//                }
//                // Email address
//                Text(
//                    text = "Email: corgrizzly@gmail.com",
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.weight(1f)
//                )
//            }
//        }
//    }
//}
//
