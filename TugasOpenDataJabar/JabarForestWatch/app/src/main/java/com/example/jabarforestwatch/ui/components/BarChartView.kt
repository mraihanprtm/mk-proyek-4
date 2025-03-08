package com.example.jabarforestwatch.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jabarforestwatch.viewmodel.HomeViewModel

@Composable
fun BarChartView(viewModel: HomeViewModel = hiltViewModel()) {
    val chartData by viewModel.chartData.collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val barWidth = size.width / (chartData.size * 2)
            val maxValue = chartData.maxOfOrNull { it.second } ?: 1.0

            chartData.forEachIndexed { index, (label, value) ->
                val barHeight = (value / maxValue) * size.height

                drawRect(
                    color = Color.Blue,
                    topLeft = androidx.compose.ui.geometry.Offset(
                        x = index * barWidth * 2 + barWidth / 2,
                        y = size.height - barHeight.toFloat()
                    ),
                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight.toFloat())
                )
            }
        }
    }
}