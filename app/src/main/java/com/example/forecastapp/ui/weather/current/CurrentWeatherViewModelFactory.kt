package com.example.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastapp.data.repo.ForecastRepo

class CurrentWeatherViewModelFactory(private val forecastRepo: ForecastRepo):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepo) as T
    }
}