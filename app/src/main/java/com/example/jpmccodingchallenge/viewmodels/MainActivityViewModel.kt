package com.example.jpmccodingchallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.jpmccodingchallenge.networking.Repository
import com.example.jpmccodingchallenge.models.SATResults
import com.example.jpmccodingchallenge.models.School
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel: ViewModel() {
    private val repository = Repository()

    fun getSchoolsList(): LiveData<List<School>?> {
        return liveData(Dispatchers.IO){
            val schoolsList = repository.getSchoolsList()
            emit(schoolsList)
        }
    }

    fun getSATResults(): LiveData<List<SATResults>?> {
        return liveData(Dispatchers.IO){
            val satResultsList = repository.getSATResults()
            emit(satResultsList)
        }
    }
}