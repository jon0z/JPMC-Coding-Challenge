package com.example.jpmccodingchallenge.models

import com.google.gson.annotations.SerializedName

data class SATResults(
    val dbn: String,
    @SerializedName("school_name") val schoolName: String,
    @SerializedName("num_of_sat_test_takers") val numberTestTakers: String,
    @SerializedName("sat_critical_reading_avg_score") val avgReadingScore: String,
    @SerializedName("sat_math_avg_score") val avgMathScore: String,
    @SerializedName("sat_writing_avg_score") val avgWritingScore: String,
)
