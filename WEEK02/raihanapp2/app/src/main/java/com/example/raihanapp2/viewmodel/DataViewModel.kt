package com.example.raihanapp2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.raihanapp2.data.AppDatabase
import com.example.raihanapp2.data.DataEntity
import com.example.raihanapp2.data.DataUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).dataDao()
    val dataList: LiveData<List<DataEntity>> = dao.getAll()

    fun insertData(
        kodeProvinsi: String,
        namaProvinsi: String,
        kodeKabupatenKota: String,
        namaKabupatenKota: String,
        total: String,
        satuan: String,
        tahun: String
    ) {
        viewModelScope.launch {
            val totalValue = total.toDoubleOrNull() ?: 0.0
            val tahunValue = tahun.toIntOrNull() ?: 0
            dao.insert(
                DataEntity(
                    kodeProvinsi = kodeProvinsi,
                    namaProvinsi = namaProvinsi,
                    kodeKabupatenKota = kodeKabupatenKota,
                    namaKabupatenKota = namaKabupatenKota,
                    total = totalValue,
                    satuan = satuan,
                    tahun = tahunValue
                )
            )
        }
    }

    fun updateData(data: DataEntity) {
        viewModelScope.launch {
            dao.update(data)
        }
    }

    suspend fun getDataById(id: Int): DataEntity? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }

    fun deleteData(data: DataEntity) {
        viewModelScope.launch {
            dao.delete(data)
        }
    }

    fun getTotalDataCount(): LiveData<Int> = dao.getTotalDataCount()

    fun getUniqueProvincesCount(): LiveData<Int> = dao.getUniqueProvincesCount()

    fun getUniqueKabupatenKotaCount(): LiveData<Int> = dao.getUniqueKabupatenKotaCount()

    val userData: LiveData<DataUser?> = dao.getUser()

    fun saveUserData(nama: String, role: String) {
        viewModelScope.launch {
            val user = DataUser(id = 1, userName = nama, userRole = role)
            dao.insertOrUpdateUser(user)
        }
    }

}
