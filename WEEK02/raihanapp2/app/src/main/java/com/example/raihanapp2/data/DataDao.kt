package com.example.raihanapp2.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<DataEntity>)

    @Insert
    suspend fun insert(data: DataEntity)

    @Update
    suspend fun update(data: DataEntity)

    @Query("SELECT * FROM data_table ORDER BY id DESC")
    fun getAll(): LiveData<List<DataEntity>>

    @Query("SELECT * FROM data_table WHERE id = :dataId")
    suspend fun getById(dataId: Int): DataEntity?

    @Delete
    suspend fun delete(data: DataEntity)

    @Query("SELECT COUNT(*) FROM data_table")
    fun getTotalDataCount(): LiveData<Int>

    @Query("SELECT COUNT(DISTINCT namaProvinsi) FROM data_table")
    fun getUniqueProvincesCount(): LiveData<Int>

    @Query("SELECT COUNT(DISTINCT namaKabupatenKota) FROM data_table")
    fun getUniqueKabupatenKotaCount(): LiveData<Int>

    @Query("SELECT SUM(total) FROM data_table")
    fun getTotalPersons(): LiveData<Int>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUser(user: DataUser)
//
//    @Update
//    suspend fun updateUser(user: DataUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: DataUser)

    @Query("SELECT * FROM data_user LIMIT 1")
    fun getUser(): LiveData<DataUser?>
}
