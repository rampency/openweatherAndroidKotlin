package com.example.androidchallenge.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.network.models.WeatherData
import com.example.androidchallenge.network.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.androidchallenge.utils.Result

class WeatherViewModel( private val weatherRepository: WeatherRepository) : ViewModel() {

    private val weather = MutableLiveData<Result<WeatherData>>()
    fun getWeather() = weather
    fun loadData() {
        try {

            viewModelScope.launch(Dispatchers.IO) {
                val response = weatherRepository.getCurrentWeather()
                response?.let {
                    val weatherList = it.body()
                    weatherList?.let { list ->
                        weather.postValue(Result.Success(list))
                    }
                }
            }
        } catch (error: Exception) {
            weather.postValue(Result.Error(error))
        }
    }
}