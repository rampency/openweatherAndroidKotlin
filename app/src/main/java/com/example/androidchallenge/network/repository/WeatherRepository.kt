package com.example.androidchallenge.network.repository

import android.util.Log
import com.example.androidchallenge.network.api.WeatherApi
import com.example.androidchallenge.network.models.WeatherData
import retrofit2.Response
import retrofit2.awaitResponse

class WeatherRepository(val apiService: WeatherApi) {
    private val TAG = WeatherRepository::class.java.name

    var lat = "40.725302"
    var lon = "-73.997776"

    suspend fun getCurrentWeather(): Response<WeatherData>? {
        try {
            var call = apiService.getWeatherForecast(lat, lon)
            var response = call.awaitResponse()
            if (!response.isSuccessful) {
                //unsuccessful
                Log.e(TAG, "Response is UnSuccessful")
                return null
            }
            return response
        } catch (error: Exception) {
            Log.e(TAG, "Error: ${error.message}")
            return null
        }
    }
}