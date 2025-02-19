package com.example.raihanapp2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.raihanapp2.viewmodel.DataViewModel

@Composable
fun DashboardScreen(navController: NavHostController, viewModel: DataViewModel) {
    val totalData by viewModel.getTotalDataCount().observeAsState(0)
    val uniqueProvinces by viewModel.getUniqueProvincesCount().observeAsState(0)
    val uniqueKabupatenKota by viewModel.getUniqueKabupatenKotaCount().observeAsState(0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        // Group 1n
        Column {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Start).padding(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Deskripsi tambahan
            Text(
                text = "Kumpulan Data Persentase Gempa Kabupaten/Kota di Jawa Barat Tiap Tahun",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Group 2
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Card 1 - Total Entri Data
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Total Entri Data", style = MaterialTheme.typography.titleMedium)
                        Text(text = "$totalData", style = TextStyle(
                            fontSize = 64.sp,
                            color = Color.Black
                        ))
                    }
                }

                // Card 2 - Total Provinsi
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Total Provinsi", style = MaterialTheme.typography.titleMedium)
                        Text(text = "$uniqueProvinces", style = TextStyle(
                            fontSize = 64.sp,
                            color = Color.Black
                        ))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Card 3
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE1BEE7))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Total Kabupaten/Kota", style = MaterialTheme.typography.titleMedium)
                        Text(text = "$uniqueKabupatenKota", style = TextStyle(
                            fontSize = 64.sp,
                            color = Color.Black
                        )
                        )
                    }
                }
            }
        }

        // Group 3: Button
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
