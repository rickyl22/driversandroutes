package com.example.lira.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DriverRepository {

    val baseUrl = "https://d49c3a78-a4f2-437d-bf72-569334dea17c.mock.pstmn.io"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}