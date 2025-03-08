package com.example.jabarforestwatch.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jabarforestwatch.viewmodel.HomeViewModel
import kotlin.math.cos
import kotlin.math.sin

@SuppressLint("DefaultLocale")
@Composable
fun PieChartView(viewModel: HomeViewModel) {
    val data by viewModel.chartData.collectAsState(initial = emptyList())

    if (data.isNotEmpty()) {
        val total = data.sumOf { it.second }
        val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow) // Warna berbeda untuk tiap kategori

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Canvas(modifier = Modifier.size(200.dp)) {
                var startAngle = 0f
                data.forEachIndexed { index, (label, value) ->
                    val sweepAngle = ((value.toFloat() / total.toFloat()) * 360f)
                    drawArc(
                        color = colors[index % colors.size],
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true
                    )
                    startAngle += sweepAngle
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                data.forEachIndexed { index, (label, value) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(colors[index % colors.size])
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "$label: ${String.format("%.2f", value)} ha",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    } else {
        Text(text = "Tidak ada data", color = Color.White, fontSize = 16.sp)
    }
}