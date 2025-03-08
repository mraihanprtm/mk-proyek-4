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
class BookmarkViewModel @Inject constructor(
    private val repository: ForestWatchRepository
) : ViewModel() {

    private val _bookmarkedData = MutableStateFlow<List<ForestDamageEntity>>(emptyList())
    val bookmarkedData: StateFlow<List<ForestDamageEntity>> = _bookmarkedData

    init {
        loadBookmarkedData()
    }

    private fun loadBookmarkedData() {
        viewModelScope.launch {
            repository.getBookmarkedData().collect { data ->
                _bookmarkedData.value = data
            }
        }
    }
}