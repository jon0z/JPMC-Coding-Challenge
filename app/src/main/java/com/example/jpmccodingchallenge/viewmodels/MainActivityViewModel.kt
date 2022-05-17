package com.example.jpmccodingchallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.jpmccodingchallenge.networking.Repository
import com.example.jpmccodingchallenge.models.School
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel: ViewModel() {
    private val repository = Repository()

    // using the liveData builder function to be able to call suspend functions
    // asynchronously in a lifecycle aware manner
    fun getSchoolsList(): LiveData<List<School>?> {
        return liveData(Dispatchers.IO){
            val schoolsList = repository.getSchoolsList()
            emit(schoolsList)
        }
    }
}