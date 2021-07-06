package com.example.androidchallenge.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidchallenge.network.api.WeatherApi
import com.example.androidchallenge.network.repository.WeatherRepository
import com.example.androidchallenge.ui.WeatherViewModel

class ViewModelFactory(private val apiService: WeatherApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(
                WeatherRepository(apiService)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}