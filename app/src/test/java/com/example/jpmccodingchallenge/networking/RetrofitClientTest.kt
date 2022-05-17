package com.example.jpmccodingchallenge.networking

import org.junit.Test
import retrofit2.Retrofit

class RetrofitClientTest {
    private var retrofitClient: Retrofit? = null
    private val actualUrl = "https://data.cityofnewyork.us/"

    @Test
    fun getClient_retrofit_not_null() {
        retrofitClient = RetrofitClient.getClient()
        assert(retrofitClient != null)
    }

    @Test
    fun getClient_valid_url(){
        retrofitClient = RetrofitClient.getClient()
        val baseUrl = retrofitClient?.baseUrl().toString()
        assert(baseUrl == actualUrl)
    }
}