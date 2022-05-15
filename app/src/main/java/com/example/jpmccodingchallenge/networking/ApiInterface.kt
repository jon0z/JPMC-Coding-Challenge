package com.example.jpmccodingchallenge.networking

import com.example.jpmccodingchallenge.models.SATResults
import com.example.jpmccodingchallenge.models.School
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchoolsList(): Response<List<School>>

    @GET("resource/f9bf-2cp4.json")
    suspend fun getSATResults(): Response<List<SATResults>>
}