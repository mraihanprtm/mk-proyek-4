package com.example.jabarforestwatch.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 1,
    val email: String,
    val name: String,
    val role: String,
    val imagePath: String? = null
)