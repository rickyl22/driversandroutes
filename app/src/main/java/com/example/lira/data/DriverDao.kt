package com.example.lira.data

import androidx.room.*


@Dao
interface DriverDao {

    @Insert
    suspend fun insertDriver(driver: Driver)

    @Query("SELECT * FROM driver_table")
    fun getAllDrivers(): List<Driver>

    @Update
    suspend fun updateDriver(driver: Driver)

    @Delete
    suspend fun deleteDriver(driver: Driver)

    @Query("DELETE FROM driver_table")
    suspend fun deleteAllDrivers()

}