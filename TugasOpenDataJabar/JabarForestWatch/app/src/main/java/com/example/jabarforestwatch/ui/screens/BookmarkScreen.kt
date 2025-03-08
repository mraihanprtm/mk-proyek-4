package com.example.jabarforestwatch.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jabarforestwatch.ui.components.Screen
import com.example.jabarforestwatch.viewmodel.BookmarkViewModel

@Composable
fun BookmarkScreen(
    navController: NavController,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val bookmarkedList by viewModel.bookmarkedData.collectAsState()

    if (bookmarkedList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Belum ada data yang di-bookmark",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(bookmarkedList) { item ->
                ForestDamageItem(forestdamage = item, onClick = {
                    navController.navigate(Screen.Detail.createRoute(item.id))
                })
            }
        }
    }
}
