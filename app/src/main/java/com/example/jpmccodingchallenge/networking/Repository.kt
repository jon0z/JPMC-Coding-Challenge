package com.example.jpmccodingchallenge.networking

import com.example.jpmccodingchallenge.models.SATResults
import com.example.jpmccodingchallenge.models.School
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// The repository class takes care of the networking operations with the API endpoints.
// This class could be expanded to include other API endpoints that help with the data manipulation
// and preparation before displaying it to the user on the UI but due to time constraint and limited
// number of requests allowed without an app token I decided to use these two given in the requirements.
class Repository {
    private val apiClient: ApiInterface = RetrofitClient.getClient().create(ApiInterface::class.java)
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