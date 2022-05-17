package com.example.jpmccodingchallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.jpmccodingchallenge.models.SATResults
import com.example.jpmccodingchallenge.networking.Repository
import kotlinx.coroutines.Dispatchers

// For this project we could have used a single view model to handle all the data
// related transactions but I decided to use a separate view model for the fragment
// for future use cases where different api calls might be made only by the StudentDetailsFragment
class SchoolDetailsViewModel : ViewModel() {
    private val repository = Repository()

    fun getSATResults(): LiveData<List<SATResults>?> {
        return liveData(Dispatchers.IO){
            val satResultsList = repository.getSATResults()
            emit(satResultsList)
        }
    }
}