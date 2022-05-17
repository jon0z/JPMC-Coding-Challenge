package com.example.jpmccodingchallenge.viewmodels

import com.example.jpmccodingchallenge.networking.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class SchoolDetailsViewModelTest {
    private var repository: Repository? = null
    @Before
    fun setUp() {
        repository = Repository()
    }

    @After
    fun tearDown() {
        repository = null
    }

    @Test
    fun getSATResults_list_not_null() {
        val satResults = runBlocking {
            return@runBlocking repository?.getSATResults()
        }
        assertNotNull(satResults)
    }
}