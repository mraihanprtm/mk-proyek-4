package com.example.jabarforestwatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jabarforestwatch.data.ForestWatchRepository
import com.example.jabarforestwatch.data.entities.ForestDamageEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ForestWatchRepository
) : ViewModel() {

    private val _forestDetail = MutableStateFlow<ForestDamageEntity?>(null)
    val forestDetail: StateFlow<ForestDamageEntity?> get() = _forestDetail

    fun getForestDamageDetail(id: Int) {
        viewModelScope.launch {
            _forestDetail.value = repository.getForestDamageDetail(id)
        }
    }

    fun updateForestDamage(updatedForestDamage: ForestDamageEntity) {
        viewModelScope.launch {
            repository.updateForestDamage(updatedForestDamage)
            _forestDetail.value = updatedForestDamage
        }
    }

    fun deleteForestDamage(id: Int, onDeleteSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.deleteForestDamage(id)
            onDeleteSuccess()
        }
    }

    fun toggleBookmark(id: Int) {
        viewModelScope.launch {
            _forestDetail.value?.let { detail ->
                val newState = detail.copy(isBookmarked = !detail.isBookmarked)
                repository.toggleBookmark(id, detail.isBookmarked)
                _forestDetail.value = newState
            }
        }
    }
}