package com.example.jabarforestwatch.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jabarforestwatch.viewmodel.HomeViewModel

@Composable
fun LineChartView(viewModel: HomeViewModel = hiltViewModel()) {
    val chartData by viewModel.chartData.collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path()
            val maxValue = chartData.maxOfOrNull { it.second } ?: 1.0
            val stepX = size.width / (chartData.size - 1).coerceAtLeast(1)

            chartData.forEachIndexed { index, (_, value) ->
                val x = index * stepX
                val y = size.height - (value / maxValue * size.height).toFloat()
                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }

            drawPath(
                path = path,
                color = Color.Red,
                style = Stroke(width = 4f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}