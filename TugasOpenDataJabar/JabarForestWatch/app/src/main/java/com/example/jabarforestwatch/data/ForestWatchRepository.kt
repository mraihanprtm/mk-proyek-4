package com.example.jabarforestwatch.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.jabarforestwatch.data.dao.ForestDamageDao
import com.example.jabarforestwatch.data.dao.ProfileDao
import com.example.jabarforestwatch.data.entities.ForestDamageEntity
import com.example.jabarforestwatch.data.entities.ProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForestWatchRepository @Inject constructor(
    private val apiService: OpenDataJabarApiService,
    private val forestDamageDao: ForestDamageDao,
    private val profileDao: ProfileDao
) {
    fun getForestDamageData(): Flow<PagingData<ForestDamageEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ForestDamagePagingSource(apiService) }
        ).flow.map { pagingData ->
            pagingData.map { net ->
                val entity = ForestDamageEntity(
                    id = net.id,
                    kode_provinsi = net.kode_provinsi,
                    nama_provinsi = net.nama_provinsi,
                    kesatuan_pengelolaan_hutan = net.kesatuan_pengelolaan_hutan,
                    jenis_gangguan_kerusakan = net.jenis_gangguan_kerusakan,
                    luas_gangguan_kerusakan = net.luas_gangguan_kerusakan,
                    satuan = net.satuan,
                    tahun = net.tahun
                )
                forestDamageDao.insert(entity)
                entity
            }
        }
    }

    suspend fun getForestDamageDetail(id: Int): ForestDamageEntity? {
        // Cek apakah data sudah ada di database
        val cachedData = forestDamageDao.getForestDamageById(id)
        if (cachedData != null) {
            return cachedData
        }

        return try {
            // Jika belum ada, ambil dari API
            val response = apiService.getForestDamageDetail(id)
            val entity = ForestDamageEntity(
                id = response.id,
                kode_provinsi = response.kode_provinsi,
                nama_provinsi = response.nama_provinsi,
                kesatuan_pengelolaan_hutan = response.kesatuan_pengelolaan_hutan,
                jenis_gangguan_kerusakan = response.jenis_gangguan_kerusakan,
                luas_gangguan_kerusakan = response.luas_gangguan_kerusakan,
                satuan = response.satuan,
                tahun = response.tahun
            )

            // Simpan ke database
            forestDamageDao.insert(entity)
            entity
        } catch (e: Exception) {
            null
        }
    }

    // Update data di database
    suspend fun updateForestDamage(forestDamage: ForestDamageEntity) {
        forestDamageDao.update(forestDamage)
    }

    // Hapus data dari database
    suspend fun deleteForestDamage(id: Int) {
        forestDamageDao.deleteById(id)
    }

    fun getBookmarkedData(): Flow<List<ForestDamageEntity>> {
        return forestDamageDao.getBookmarkedData()
    }

    suspend fun toggleBookmark(id: Int, currentState: Boolean) {
        forestDamageDao.updateBookmarkStatus(id, !currentState)
    }

    suspend fun saveProfileImagePath(path: String) {
        val currentProfile = profileDao.getProfile() ?: ProfileEntity(id = 1, email = "", name = "", role = "")
        val updatedProfile = currentProfile.copy(imagePath = path)
        profileDao.insert(updatedProfile)
    }

    suspend fun getProfileImagePath(): String? {
        return profileDao.getProfileImagePath()
    }

    suspend fun getProfile(): ProfileEntity? {
        return profileDao.getProfile()
    }

    suspend fun saveProfile(profile: ProfileEntity) {
        profileDao.insert(profile)
    }

    suspend fun getTotalForestDamage(): Double {
        return forestDamageDao.getTotalForestDamage() ?: 0.0
    }

    suspend fun getTotalKPH(): Int {
        return forestDamageDao.getTotalKPH() ?: 0
    }

    suspend fun getYearRange(): String {
        val minYear = forestDamageDao.getMinYear() ?: 0
        val maxYear = forestDamageDao.getMaxYear() ?: 0
        return if (minYear > 0 && maxYear > 0) "$minYear - $maxYear" else "Tidak Ada Data"
    }

    suspend fun getMostFrequentDamageType(): String {
        return forestDamageDao.getMostFrequentDamageType() ?: "Tidak Diketahui"
    }

    suspend fun getForestDamageGroupedByType(): List<Pair<String, Double>> {
        return forestDamageDao.getForestDamageGroupedByType().map { it.jenis_gangguan_kerusakan to it.total_luas }
    }

}