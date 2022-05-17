package com.example.jpmccodingchallenge.networking

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class RepositoryTest {
    private var retrofitClient: ApiInterface? = null

    @Before
    fun setUp() {
        retrofitClient = RetrofitClient.getClient().create(ApiInterface::class.java)
    }

    @After
    fun tearDown() {
        retrofitClient = null
    }

    @Test
    fun getSchoolsList_response_not_null() {
        val schoolsResponse = runBlocking {
            return@runBlocking retrofitClient?.getSchoolsList()
        }
        assertNotNull(schoolsResponse)
    }

    @Test
    fun getSATResults_response_not_null() {
        val satResultsResponse = runBlocking {
            return@runBlocking retrofitClient?.getSATResults()
        }
        assertNotNull(satResultsResponse)
    }
}