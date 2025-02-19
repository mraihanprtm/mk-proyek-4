package com.example.raihanapp2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [DataEntity::class, DataUser::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = database.dataDao()

                    // User Dummy
                    val demoUser = DataUser(id = 1, userName = "Demo User", userRole = "Admin")
                    dao.insertOrUpdateUser(demoUser)

                    // Tambahkan Data Awal - masih belum berhasil
                    dao.insertAll(getInitialData())
                }
            }
        }

        fun getInitialData(): List<DataEntity> {
            return listOf(
                DataEntity(kodeProvinsi = "11", namaProvinsi = "Aceh", kodeKabupatenKota = "1101", namaKabupatenKota = "Banda Aceh", total = 100.0, satuan = "Ton", tahun = 2023),
                DataEntity(kodeProvinsi = "12", namaProvinsi = "Sumatera Utara", kodeKabupatenKota = "1201", namaKabupatenKota = "Medan", total = 150.0, satuan = "Ton", tahun = 2023),
                DataEntity(kodeProvinsi = "13", namaProvinsi = "Sumatera Barat", kodeKabupatenKota = "1301", namaKabupatenKota = "Padang", total = 120.5, satuan = "Ton", tahun = 2023),
                DataEntity(kodeProvinsi = "14", namaProvinsi = "Riau", kodeKabupatenKota = "1401", namaKabupatenKota = "Pekanbaru", total = 180.0, satuan = "Ton", tahun = 2023),
                DataEntity(kodeProvinsi = "15", namaProvinsi = "Jambi", kodeKabupatenKota = "1501", namaKabupatenKota = "Jambi", total = 90.0, satuan = "Ton", tahun = 2023),
                DataEntity(kodeProvinsi = "16", namaProvinsi = "Sumatera Selatan", kodeKabupatenKota = "1601", namaKabupatenKota = "Palembang", total = 200.0, satuan = "Ton", tahun = 2023),
                DataEntity(kodeProvinsi = "17", namaProvinsi = "Bengkulu", kodeKabupatenKota = "1701", namaKabupatenKota = "Bengkulu", total = 80.0, satuan = "Ton", tahun = 2023),
                DataEntity(kodeProvinsi = "18", namaProvinsi = "Lampung", kodeKabupatenKota = "1801", namaKabupatenKota = "Bandar Lampung", total = 175.0, satuan = "Ton", tahun = 2023),
                DataEntity(kodeProvinsi = "19", namaProvinsi = "Kepulauan Bangka Belitung", kodeKabupatenKota = "1901", namaKabupatenKota = "Pangkal Pinang", total = 95.0, satuan = "Ton", tahun = 2023),
                DataEntity(kodeProvinsi = "21", namaProvinsi = "Kepulauan Riau", kodeKabupatenKota = "2101", namaKabupatenKota = "Tanjung Pinang", total = 110.0, satuan = "Ton", tahun = 2023)
            )
        }
    }
}