package com.firozpoc.weatherforecastandroidapplication.model

data class Timeline(
    val endTime: String,
    val intervals: List<Interval>,
    val startTime: String,
    val timestep: String
)