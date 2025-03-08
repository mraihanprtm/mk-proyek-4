package com.example.jabarforestwatch.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jabarforestwatch.data.dao.ForestDamageDao
import com.example.jabarforestwatch.data.dao.ProfileDao
import com.example.jabarforestwatch.data.entities.ForestDamageEntity
import com.example.jabarforestwatch.data.entities.ProfileEntity

@Database(entities = [ForestDamageEntity::class, ProfileEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun forestDamageDao(): ForestDamageDao
    abstract fun profileDao(): ProfileDao
}