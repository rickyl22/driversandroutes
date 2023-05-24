package com.example.lira.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lira.data.Driver
import com.example.lira.data.DriverDao
import com.example.lira.data.Route
import com.example.lira.data.RouteDao


@Database(entities = [Driver::class, Route::class], version = 4)
abstract class DriverDatabase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun routeDao(): RouteDao
}