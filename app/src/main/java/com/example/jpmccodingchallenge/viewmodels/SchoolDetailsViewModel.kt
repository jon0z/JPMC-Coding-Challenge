package com.example.jpmccodingchallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.jpmccodingchallenge.models.SATResults
import com.example.jpmccodingchallenge.networking.Repository
import kotlinx.coroutines.Dispatchers

class SchoolDetailsViewModel : ViewModel() {
    private val repository = Repository()

    fun getSATResults(): LiveData<List<SATResults>?> {
        return liveData(Dispatchers.IO){
            val satResultsList = repository.getSATResults()
            emit(satResultsList)
        }
    }
}