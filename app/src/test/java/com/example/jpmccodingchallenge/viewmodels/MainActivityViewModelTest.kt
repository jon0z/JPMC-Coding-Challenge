package com.example.jpmccodingchallenge.viewmodels

import com.example.jpmccodingchallenge.networking.Repository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class MainActivityViewModelTest {
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
    fun getSchoolsList_list_not_null() {
        val schoolsList = runBlocking {
            return@runBlocking repository?.getSchoolsList()
        }
        assertNotNull(schoolsList)
    }
}