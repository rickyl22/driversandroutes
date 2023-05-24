package com.example.lira.network

import com.example.lira.data.DriverResponse
import retrofit2.Response
import retrofit2.http.GET

interface DriverApi {

   @GET("/data")
   suspend fun getDrivers(): Response<DriverResponse>

}