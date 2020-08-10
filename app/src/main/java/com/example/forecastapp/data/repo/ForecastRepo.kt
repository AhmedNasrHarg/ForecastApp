package com.example.forecastapp.data.repo

import androidx.lifecycle.LiveData
import com.example.forecastapp.data.db.entity.CurrentWeatherEntry

interface ForecastRepo {
    suspend fun getCurrentWeather():LiveData<CurrentWeatherEntry>
}