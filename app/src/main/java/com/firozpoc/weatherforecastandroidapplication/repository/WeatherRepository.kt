package com.firozpoc.weatherforecastandroidapplication.repository

import com.firozpoc.weatherforecastandroidapplication.api.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getWeather() = apiService.getWeather()
}