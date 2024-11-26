package com.example.appcontact

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SuggestionsScreen() {
    val suggestions = listOf(
        "Clean up duplicates",
        "Found new contact information"
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Suggestions",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(suggestions) { suggestion ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.elevatedCardElevation(4.dp)
                ) {
                    Text(
                        text = suggestion,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}