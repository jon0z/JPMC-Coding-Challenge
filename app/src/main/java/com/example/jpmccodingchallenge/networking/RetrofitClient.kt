package com.example.jpmccodingchallenge.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Retrofit client as singleton to make sure we have one instance used throughout the app
// Some of the optimizations I would like to implement are interceptors.
// A logging interceptor to get information about each call in the logcat for debug purposes and a
// headers interceptor to verify each calls has the correct headers required by the server.
// Another optimization will be to take advantage of the okHttp authenticator, if we were to create
// an account with app tokens to identify the app with the api get access to all the
// other api endpoints without restrictions
object RetrofitClient {
    private const val BASE_URL = "https://data.cityofnewyork.us/"

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