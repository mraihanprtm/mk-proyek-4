package com.example.jabarforestwatch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jabarforestwatch.viewmodel.DetailViewModel
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: Int,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        viewModel.getForestDamageDetail(id)
    }

    val forestDetail by viewModel.forestDetail.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Kerusakan Hutan") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleBookmark(id) }) {
                        Icon(
                            imageVector = if (forestDetail?.isBookmarked == true) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Bookmark"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            forestDetail?.let { detail ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Provinsi: ${detail.nama_provinsi}", style = MaterialTheme.typography.titleLarge)
                    Text("KPH: ${detail.kesatuan_pengelolaan_hutan}", style = MaterialTheme.typography.bodyMedium)
                    Text("Jenis Gangguan: ${detail.jenis_gangguan_kerusakan}", style = MaterialTheme.typography.bodyMedium)
                    Text("Luas: ${detail.luas_gangguan_kerusakan} ${detail.satuan}", style = MaterialTheme.typography.bodyMedium)
                    Text("Tahun: ${detail.tahun}", style = MaterialTheme.typography.bodyMedium)
                }
            } ?: CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}