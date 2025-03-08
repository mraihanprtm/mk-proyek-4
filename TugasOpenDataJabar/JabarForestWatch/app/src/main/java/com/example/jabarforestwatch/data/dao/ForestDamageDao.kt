package com.example.jabarforestwatch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jabarforestwatch.data.entities.ForestDamageEntity
import com.example.jabarforestwatch.data.entities.ForestDamageTypeSum
import kotlinx.coroutines.flow.Flow

@Dao
interface ForestDamageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forestdamage: ForestDamageEntity)

    @Query("SELECT * FROM forest_damage WHERE id = :id")
    suspend fun getForestDamageById(id: Int): ForestDamageEntity?

    @Update
    suspend fun update(forestdamage: ForestDamageEntity)

    @Query("DELETE FROM forest_damage WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM forest_damage WHERE isBookmarked = 1")
    fun getBookmarkedData(): Flow<List<ForestDamageEntity>>

    @Query("UPDATE forest_damage SET isBookmarked = :state WHERE id = :id")
    suspend fun updateBookmarkStatus(id: Int, state: Boolean)

    // Total luas kerusakan hutan
    @Query("SELECT SUM(luas_gangguan_kerusakan) FROM forest_damage")
    suspend fun getTotalForestDamage(): Double?

    // Menghitung jumlah KPH yang unik
    @Query("SELECT COUNT(DISTINCT kesatuan_pengelolaan_hutan) FROM forest_damage")
    suspend fun getTotalKPH(): Int?

    // Mendapatkan rentang tahun laporan (min - max)
    @Query("SELECT MIN(tahun) FROM forest_damage")
    suspend fun getMinYear(): Int?

    @Query("SELECT MAX(tahun) FROM forest_damage")
    suspend fun getMaxYear(): Int?

    // Mendapatkan jenis gangguan kerusakan yang paling banyak terjadi
    @Query("""
        SELECT jenis_gangguan_kerusakan 
        FROM forest_damage 
        GROUP BY jenis_gangguan_kerusakan 
        ORDER BY COUNT(*) DESC 
        LIMIT 1
    """)
    suspend fun getMostFrequentDamageType(): String?

    @Query("""
    SELECT jenis_gangguan_kerusakan AS jenis_gangguan_kerusakan, 
           SUM(luas_gangguan_kerusakan) AS total_luas 
    FROM forest_damage 
    GROUP BY jenis_gangguan_kerusakan
""")
    suspend fun getForestDamageGroupedByType(): List<ForestDamageTypeSum>

}