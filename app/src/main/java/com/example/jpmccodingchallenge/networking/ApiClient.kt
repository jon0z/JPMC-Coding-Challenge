package com.example.jpmccodingchallenge.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val BASE_URL = "https://data.cityofnewyork.us/"

    fun getClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client( okHttpClient
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS).build() )
            .build()
    }
}