package com.example.raihanapp2.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.raihanapp2.R
import com.example.raihanapp2.viewmodel.DataViewModel
import kotlinx.coroutines.launch

@Composable
fun Profile(navController: NavHostController, viewModel: DataViewModel) {
    val userData by viewModel.userData.observeAsState()

    var namaUser by remember { mutableStateOf(userData?.userName ?: "John Doe") }
    var roleUser by remember { mutableStateOf(userData?.userRole ?: "Software Engineer") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(userData) {
        userData?.let {
            namaUser = it.userName
            roleUser = it.userRole
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(100.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.my_photo),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable { /* Handle image selection */ }
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = namaUser, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = roleUser, fontSize = 16.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "Edit User Information", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    Text(text = "Nama", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        value = namaUser,
                        onValueChange = { namaUser = it },
                        label = { Text("Nama") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(text = "User Role", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        value = roleUser,
                        onValueChange = { roleUser = it },
                        label = { Text("User Role") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.saveUserData(namaUser, roleUser)

                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Data berhasil diperbarui!", duration = SnackbarDuration.Short)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}