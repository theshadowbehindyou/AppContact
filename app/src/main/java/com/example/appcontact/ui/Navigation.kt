package com.example.appcontact.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appcontact.model.ContactViewModel
import com.example.appcontact.ui.AddContactScreen
import com.example.appcontact.ui.ContactDetailScreen
import com.example.appcontact.ui.ContactListScreen
import com.example.appcontact.ui.EditContactScreen
import com.example.appcontact.ui.SearchContactScreen

@Composable
fun MainApp(navController: NavHostController = rememberNavController(), viewModel: ContactViewModel) {
//    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = "contact_list") {
        composable("contact_list") {
            ContactListScreen(viewModel = viewModel, navController = navController)
        }
        composable("search_contact") {
            SearchContactScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            route = "contact_detail/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: 0
            ContactDetailScreen(viewModel = viewModel, contactId = contactId, navController = navController)
        }
        composable("add_contact") {
            AddContactScreen(viewModel = viewModel, navController = navController, context = context)
        }
        composable(
            route = "edit_contact/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: 0
            EditContactScreen(contactId = contactId, viewModel = viewModel, navController = navController, context = context)
        }
    }
}