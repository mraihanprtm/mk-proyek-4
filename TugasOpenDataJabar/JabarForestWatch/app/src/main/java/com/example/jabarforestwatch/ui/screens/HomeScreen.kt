package com.example.jabarforestwatch.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jabarforestwatch.ui.components.BarChartView
import com.example.jabarforestwatch.ui.components.LineChartView
import com.example.jabarforestwatch.ui.components.PieChartView
import com.example.jabarforestwatch.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val totalDamage by viewModel.totalDamage.collectAsState(initial = "0")
    val totalKPH by viewModel.totalKPH.collectAsState(initial = "0")
    val yearRange by viewModel.yearRange.collectAsState(initial = "N/A")
    val mostFrequentDamageType by viewModel.mostFrequentDamageType.collectAsState(initial = "N/A")

    var selectedChart by remember { mutableStateOf("Pie Chart") }
    val chartOptions = listOf("Pie Chart", "Bar Chart", "Line Chart")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Tambahkan scroll agar tidak terpotong
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Dashboard",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Section 1: 4 Card
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DashboardCard(
                    title = "Total Kerusakan",
                    value = "$totalDamage ha",
                    backgroundColor = Color(0xFFFF6F61),
                    textColor = Color.White,
                    modifier = Modifier.weight(1f)
                )
                DashboardCard(
                    title = "Total KPH",
                    value = "$totalKPH KPH",
                    backgroundColor = Color(0xFF6B8E23),
                    textColor = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DashboardCard(
                    title = "Rentang Tahun",
                    value = yearRange,
                    backgroundColor = Color(0xFF4682B4),
                    textColor = Color.White,
                    modifier = Modifier.weight(1f)
                )
                DashboardCard(
                    title = "Laporan Terbanyak",
                    value = mostFrequentDamageType,
                    backgroundColor = Color(0xFFFFD700),
                    textColor = Color.Black,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section 2: Chart Selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            chartOptions.forEach { option ->
                Button(
                    onClick = { selectedChart = option },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedChart == option) Color.Gray else Color.LightGray
                    ),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(option)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section 2: Chart Display
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (selectedChart) {
                "Pie Chart" -> PieChartView(viewModel)
                "Bar Chart" -> BarChartView(viewModel)
                "Line Chart" -> LineChartView(viewModel)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun DashboardCard(title: String, value: String, backgroundColor: Color, textColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}