package com.example.jabarforestwatch.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forest_damage")
data class ForestDamageEntity(
    @PrimaryKey val id: Int,
    val kode_provinsi: Int,
    val nama_provinsi: String,
    val kesatuan_pengelolaan_hutan: String,
    val jenis_gangguan_kerusakan: String,
    val luas_gangguan_kerusakan: Double,
    val satuan: String,
    val tahun: Int,
    var isBookmarked: Boolean = false
)