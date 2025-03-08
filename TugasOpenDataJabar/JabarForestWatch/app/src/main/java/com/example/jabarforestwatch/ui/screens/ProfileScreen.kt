package com.example.jabarforestwatch.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.jabarforestwatch.viewmodel.ProfileViewModel
import java.io.File

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val profile by viewModel.profile.collectAsState()
    val profileImagePath by viewModel.profileImagePath.collectAsState()
    val isPhotoUpdated by viewModel.isPhotoUpdated.collectAsState()

    val context = LocalContext.current

    var isEditing by remember { mutableStateOf(false) }
    var tempName by remember { mutableStateOf("") }
    var tempEmail by remember { mutableStateOf("") }
    var tempRole by remember { mutableStateOf("") }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showPhotoSavedDialog by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
    }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    LaunchedEffect(profile) {
        profile?.let {
            tempName = it.name
            tempEmail = it.email
            tempRole = it.role
        }
    }

    LaunchedEffect(profileImagePath) {
        selectedImageUri = null
    }

    LaunchedEffect(isPhotoUpdated) {
        if (isPhotoUpdated) {
            showPhotoSavedDialog = true
            viewModel.resetPhotoUpdateState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Foto Profil (Sekarang benar-benar lingkaran penuh)
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            when {
                selectedImageUri != null -> {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Preview Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()  // Gambar mengisi area penuh
                            .clip(CircleShape)  // Pastikan lingkaran
                    )
                }
                profileImagePath != null -> {
                    val file = File(profileImagePath!!)
                    AsyncImage(
                        model = file,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
                else -> {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Default Profile",
                        modifier = Modifier.size(80.dp),
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Informasi Profil
        Text(text = profile?.name ?: "Nama belum diisi", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = profile?.email ?: "Email belum diisi", fontSize = 16.sp, color = Color.Gray)
        Text(text = profile?.role ?: "Role belum diisi", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Tombol Edit Informasi
        Button(onClick = { isEditing = true }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ubah Informasi")
        }

        if (isEditing) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                TextField(value = tempName, onValueChange = { tempName = it }, label = { Text("Nama") })
                TextField(value = tempEmail, onValueChange = { tempEmail = it }, label = { Text("Email") })
                TextField(value = tempRole, onValueChange = { tempRole = it }, label = { Text("Role") })

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    viewModel.updateProfile(tempName, tempEmail, tempRole)
                    isEditing = false
                }) {
                    Text("Simpan Perubahan")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Tombol Ubah Foto Profil
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Pilih Foto")
        }

        if (selectedImageUri != null) {
            Button(onClick = {
                selectedImageUri?.let { uri ->
                    viewModel.setProfileImage(uri)
                }
            }) {
                Text("Simpan Foto")
            }
        }

        // 🔹 Dialog Popup Konfirmasi
        if (showPhotoSavedDialog) {
            AlertDialog(
                onDismissRequest = { showPhotoSavedDialog = false },
                confirmButton = {
                    Button(onClick = { showPhotoSavedDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Berhasil!") },
                text = { Text("Foto profil telah berhasil diperbarui.") }
            )
        }
    }
}