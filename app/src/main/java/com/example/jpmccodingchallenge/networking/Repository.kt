package com.example.jpmccodingchallenge.networking

import com.example.jpmccodingchallenge.models.SATResults
import com.example.jpmccodingchallenge.models.School
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    private val apiClient: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO

    suspend fun getSchoolsList(): List<School>? = withContext(ioDispatchers){
        val response = apiClient.getSchoolsList()
        if(response.isSuccessful && response.body() != null) {
            return@withContext response.body()
        } else {
            return@withContext null
        }
    }

    suspend fun getSATResults(): List<SATResults>? = withContext(ioDispatchers){
        val response = apiClient.getSATResults()
        if(response.isSuccessful && response.body() != null){
            return@withContext response.body()
        } else {
            return@withContext null
        }
    }
}