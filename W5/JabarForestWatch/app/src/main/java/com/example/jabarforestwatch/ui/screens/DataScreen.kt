package com.example.jabarforestwatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jabarforestwatch.data.entities.ForestDamageEntity
import com.example.jabarforestwatch.viewmodel.DataViewModel

@Composable
fun DataScreen(
    onForestClick: (Int) -> Unit,
    viewModel: DataViewModel = hiltViewModel()
) {
    val forestDamageList = viewModel.forestDamageList.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(items = List(forestDamageList.itemCount) { it }) { index, _ ->
            forestDamageList[index]?.let { forestdamage ->
                ForestDamageItem(
                    forestdamage = forestdamage,
                    onClick = { onForestClick(forestdamage.id) }
                )
            }
        }
    }
}

@Composable
fun ForestDamageItem(forestdamage: ForestDamageEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "KPH: ${forestdamage.kesatuan_pengelolaan_hutan}",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Gangguan: ${forestdamage.jenis_gangguan_kerusakan}",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Luas: ${forestdamage.luas_gangguan_kerusakan} ha",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Tahun: ${forestdamage.tahun}",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}