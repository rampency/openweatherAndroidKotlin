package com.example.androidchallenge.network.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    var BaseUrl = "https://api.openweathermap.org/"

    private var retrofit = Retrofit.Builder()
        .baseUrl(BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun <T> createService(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}