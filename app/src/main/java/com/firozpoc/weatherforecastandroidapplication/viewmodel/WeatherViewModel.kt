package com.firozpoc.weatherforecastandroidapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firozpoc.weatherforecastandroidapplication.model.Values
import com.firozpoc.weatherforecastandroidapplication.model.Weather
import com.firozpoc.weatherforecastandroidapplication.repository.WeatherRepository
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel(){

    private val _resp = MutableLiveData<Values>()
    val weatherResponse : LiveData<Values>
    get() = _resp


    init {
        getWeather()
    }

    private fun getWeather() = viewModelScope.launch {
        repository.getWeather().let { response ->
            if (response.isSuccessful){
                _resp.postValue(response.body())
            }else{
                Log.d("Tag", "getWeather Error Response: ${response.message()}")
            }
        }
    }
}