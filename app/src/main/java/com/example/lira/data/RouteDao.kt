package com.example.lira.data

import androidx.room.*

@Dao
interface RouteDao {

    @Insert
    suspend fun insertRoute(route: Route)

    @Query("SELECT * FROM route_table")
    fun getAllRoutes(): List<Route>

    @Update
    suspend fun updateRoute(route: Route)

    @Delete
    suspend fun deleteRoute(route: Route)

    @Query("DELETE FROM route_table")
    suspend fun deleteAllRoute()

}