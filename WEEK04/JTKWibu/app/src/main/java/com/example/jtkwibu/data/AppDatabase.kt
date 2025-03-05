package com.example.jtkwibu.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AnimeEntity::class, ProfileEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun profileDao(): ProfileDao
}