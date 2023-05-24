package com.example.lira.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.lira.data.Driver
import com.example.lira.data.DriverDao
import com.example.lira.data.Route
import com.example.lira.data.RouteDao
import com.example.lira.db.DriverDatabase
import com.example.lira.network.DriverRepository
import com.example.lira.network.DriverApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DriverViewModel(var application: Application) : ViewModel() {

    data class ViewState(
        val drivers: ArrayList<Driver> = ArrayList(),
        val routes: ArrayList<Route> = ArrayList(),
        )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()
    private var usersApi: DriverApi = DriverRepository.getInstance().create(DriverApi::class.java)
    private val db = Room.databaseBuilder(application, DriverDatabase::class.java, "driver_database")
        .fallbackToDestructiveMigration()
        .build()

    suspend fun insert(driver: Driver) {
        db.driverDao().insertDriver(driver)
    }

    suspend fun insert(route: Route) {
        db.routeDao().insertRoute(route)
    }

    suspend fun getAllDrivers(): List<Driver> {
        return db.driverDao().getAllDrivers()
    }

    suspend fun getAllRoutes(): List<Route> {
        return db.routeDao().getAllRoutes()
    }

    suspend fun delete() {
        db.driverDao().deleteAllDrivers()
        db.routeDao().deleteAllRoute()
    }

    fun getList() {
        viewModelScope.launch {
            val result = usersApi.getDrivers().body()!!
            _viewState.value = _viewState.value.copy(drivers = result.drivers, routes = result.routes)
        }
    }

}