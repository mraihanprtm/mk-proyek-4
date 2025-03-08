package com.example.jabarforestwatch.data

import com.google.gson.annotations.SerializedName

data class ForestDamageSearchResponse(
    @SerializedName("data") val data: List<ForestDamageModels>
)

data class ForestDamageModels(
    @SerializedName("id") val id: Int,
    @SerializedName("kode_provinsi") val kode_provinsi: Int,
    @SerializedName("nama_provinsi") val nama_provinsi: String,
    @SerializedName("kesatuan_pengelolaan_hutan") val kesatuan_pengelolaan_hutan: String,
    @SerializedName("jenis_gangguan_kerusakan") val jenis_gangguan_kerusakan: String,
    @SerializedName("luas_gangguan_kerusakan") val luas_gangguan_kerusakan: Double,
    @SerializedName("satuan") val satuan: String,
    @SerializedName("tahun") val tahun: Int,
)