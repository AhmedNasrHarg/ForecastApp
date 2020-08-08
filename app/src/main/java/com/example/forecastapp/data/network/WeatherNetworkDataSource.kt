package com.example.forecastapp.data.network

import androidx.lifecycle.LiveData
import com.example.forecastapp.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather:LiveData<CurrentWeatherResponse>
    suspend fun fetchCurrentWeather(
        location: String,
        langCode: String
    )
}