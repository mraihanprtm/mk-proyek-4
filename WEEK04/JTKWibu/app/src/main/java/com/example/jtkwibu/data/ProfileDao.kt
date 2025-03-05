package com.example.jtkwibu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jtkwibu.data.ProfileEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfileEntity)

    @Query("SELECT imagePath FROM profile_table WHERE id = 1")
    suspend fun getProfileImagePath(): String?
}
