package com.example.raihanapp2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_user")
data class DataUser(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val userName: String,
    val userRole: String,
//    val userProfile: String,
)
