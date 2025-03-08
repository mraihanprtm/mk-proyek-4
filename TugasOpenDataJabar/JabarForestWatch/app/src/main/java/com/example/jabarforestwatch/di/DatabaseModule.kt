package com.example.jabarforestwatch.di

import android.content.Context
import androidx.room.Room
import com.example.jabarforestwatch.data.AppDatabase
import com.example.jabarforestwatch.data.dao.ForestDamageDao
import com.example.jabarforestwatch.data.dao.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "jabarforestwatch_db"
        ).build()
    }

    @Provides
    fun provideForestDamageDao(db: AppDatabase): ForestDamageDao = db.forestDamageDao()

    @Provides
    fun provideProfileDao(db: AppDatabase): ProfileDao = db.profileDao()
}