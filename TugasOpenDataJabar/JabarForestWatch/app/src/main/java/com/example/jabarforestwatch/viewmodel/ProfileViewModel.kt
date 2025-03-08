package com.example.jabarforestwatch.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jabarforestwatch.data.ForestWatchRepository
import com.example.jabarforestwatch.data.entities.ProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ForestWatchRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _profile = MutableStateFlow<ProfileEntity?>(null)
    val profile = _profile.asStateFlow()

    private val _profileImagePath = MutableStateFlow<String?>(null)
    val profileImagePath = _profileImagePath.asStateFlow()

    private val _isPhotoUpdated = MutableStateFlow(false)
    val isPhotoUpdated = _isPhotoUpdated.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            repository.getProfile()?.let { data ->
                _profile.value = data
                _profileImagePath.value = data.imagePath
            }
        }
    }

    fun updateProfile(name: String, email: String, role: String) {
        viewModelScope.launch {
            val currentProfile = _profile.value ?: ProfileEntity(name = "", email = "", role = "")
            val updatedProfile = currentProfile.copy(name = name, email = email, role = role)
            repository.saveProfile(updatedProfile)
            _profile.value = updatedProfile
        }
    }

    fun setProfileImage(uri: Uri) {
        viewModelScope.launch {
            val file = File(context.filesDir, "profile_image.jpg")
            context.contentResolver.openInputStream(uri)?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            repository.saveProfileImagePath(file.absolutePath)

            loadProfile()

            _isPhotoUpdated.value = true
        }
    }

    fun resetPhotoUpdateState() {
        _isPhotoUpdated.value = false
    }
}