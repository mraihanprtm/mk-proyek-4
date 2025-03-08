package com.example.jabarforestwatch.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenDataJabarApiService {
    @GET("od_18224_luas_gangguan_kerusakan_hutan__kesatuan_pengelolaa_v1")
    suspend fun getForestDamageData(@Query("page") page: Int): ForestDamageSearchResponse

    @GET("/od_18224_luas_gangguan_kerusakan_hutan__kesatuan_pengelolaa_v1/{id}")
    suspend fun getForestDamageDetail(@Path("id") id: Int): ForestDamageModels
}