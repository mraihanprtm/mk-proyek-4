package com.example.jabarforestwatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jabarforestwatch.data.ForestWatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ForestWatchRepository
) : ViewModel() {

    private val _totalDamage = MutableStateFlow(0.0)
    val totalDamage = _totalDamage.asStateFlow()

    private val _totalKPH = MutableStateFlow(0)
    val totalKPH = _totalKPH.asStateFlow()

    private val _yearRange = MutableStateFlow("Tidak Ada Data")
    val yearRange = _yearRange.asStateFlow()

    private val _mostFrequentDamageType = MutableStateFlow("Tidak Diketahui")
    val mostFrequentDamageType = _mostFrequentDamageType.asStateFlow()

    private val _chartData = MutableStateFlow<List<Pair<String, Double>>>(emptyList())
    val chartData = _chartData.asStateFlow()

    init {
        fetchDashboardData()
    }

    private fun fetchDashboardData() {
        viewModelScope.launch {
            _totalDamage.value = repository.getTotalForestDamage()
            _totalKPH.value = repository.getTotalKPH()
            _yearRange.value = repository.getYearRange()
            _mostFrequentDamageType.value = repository.getMostFrequentDamageType()

            _chartData.value = repository.getForestDamageGroupedByType()
        }
    }
}