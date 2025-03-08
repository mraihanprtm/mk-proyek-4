package com.example.jabarforestwatch.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jabarforestwatch.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    id: Int,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    var namaProvinsi by remember { mutableStateOf("") }
    var jenisKerusakan by remember { mutableStateOf("") }
    var luasKerusakan by remember { mutableStateOf("") }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val forestDetail by viewModel.forestDetail.collectAsState()

    LaunchedEffect(id) {
        viewModel.getForestDamageDetail(id)
    }

    LaunchedEffect(forestDetail) {
        forestDetail?.let {
            namaProvinsi = it.nama_provinsi
            jenisKerusakan = it.jenis_gangguan_kerusakan
            luasKerusakan = it.luas_gangguan_kerusakan.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Data") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = namaProvinsi,
                onValueChange = { namaProvinsi = it },
                label = { Text("Nama Provinsi") }
            )
            OutlinedTextField(
                value = jenisKerusakan,
                onValueChange = { jenisKerusakan = it },
                label = { Text("Jenis Kerusakan") }
            )
            OutlinedTextField(
                value = luasKerusakan,
                onValueChange = { luasKerusakan = it },
                label = { Text("Luas Kerusakan") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = { showConfirmDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }
        }
    }

    // Dialog konfirmasi update
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Konfirmasi Perubahan") },
            text = { Text("Apakah Anda yakin ingin menyimpan perubahan ini?") },
            confirmButton = {
                Button(onClick = {
                    forestDetail?.let {
                        val updatedData = it.copy(
                            nama_provinsi = namaProvinsi,
                            jenis_gangguan_kerusakan = jenisKerusakan,
                            luas_gangguan_kerusakan = luasKerusakan.toDoubleOrNull() ?: it.luas_gangguan_kerusakan
                        )
                        viewModel.updateForestDamage(updatedData)
                        navController.popBackStack()
                    }
                    showConfirmDialog = false
                }) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}