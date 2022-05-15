package com.example.jpmccodingchallenge.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.jpmccodingchallenge.R
import com.example.jpmccodingchallenge.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getSchoolsList().observe(this) {
            if (it.isNullOrEmpty()) {
                Log.e("MainActivity", "Something happened. Failed to get school list")
            } else {
                it.forEach {
                    Log.e("MainActivity", "school name = ${it.schoolName}")
                }
            }
        }

        viewModel.getSATResults().observe(this){
            if (it.isNullOrEmpty()){
                Log.e("MainActivity", "Something happened. Failed to get SAT results list")
            } else {
                it.forEach {
                    Log.e("MainActivity", "school name = ${it.schoolName}, math avg = ${it.avgMathScore}, reading avg = ${it.avgReadingScore}, writing avg = ${it.avgWritingScore}")
                }
            }
        }

    }
}