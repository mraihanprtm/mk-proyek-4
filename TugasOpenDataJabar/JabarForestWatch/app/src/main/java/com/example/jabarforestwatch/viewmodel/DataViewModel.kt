package com.example.jabarforestwatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jabarforestwatch.data.ForestWatchRepository
import com.example.jabarforestwatch.data.entities.ForestDamageEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: ForestWatchRepository
) : ViewModel() {
    val forestDamageList: Flow<PagingData<ForestDamageEntity>> = repository.getForestDamageData()
        .cachedIn(viewModelScope)
}