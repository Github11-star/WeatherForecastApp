package com.firozpoc.weatherforecastandroidapplication.api

import com.firozpoc.weatherforecastandroidapplication.model.Values
import com.firozpoc.weatherforecastandroidapplication.model.Weather
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v4/timelines?location=26.5215,88.7196&fields=temperature,windDirection,windGust&timesteps=current&units=metric&apikey=8QzUhO519g8DLkPX0XGtJvbh0CGtvUVf")
    suspend fun getWeather() : Response<Values>
}