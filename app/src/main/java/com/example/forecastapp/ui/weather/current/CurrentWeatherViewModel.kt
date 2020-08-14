package com.example.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastapp.data.repo.ForecastRepo
import com.example.forecastapp.internal.lazyDeffered

class CurrentWeatherViewModel(
    private val forecastRepo:ForecastRepo
) : ViewModel() {
    // TODO: Implement the ViewModel
    /*

    val weather = forecastRepo.getCurrentWeather()

    this is compile error coz it is not called from a coroutine or a suspend function
    another problem is that when a CurrentWeatherViewModel object is instantiated,
    weather property will be gotten immediately.

    solution of immediate initialize to weather is using lazy with this property,
    so instead of initializing it when CurrentWeatherViewModel object is instantiated,
     it will wait for it to be called, so when weather is called it will check
     if weather is initialized it will not call forecastRepo.getCurrentWeather() again,
     if it is not initialized, it will call forecastRepo.getCurrentWeather() and will start the value
     as LiveData
     //ex
      val weather by lazy{
        forecastRepo.getCurrentWeather()
      }

      another problem now it that forecastRepo.getCurrentWeather() needs to be called within
      a coroutine context or suspend function and lazy does not provide it right now.

      solution of that problem is to create our own lazy function: its name is lazyDeffered

    */
    val weather by lazyDeffered {
        forecastRepo.getCurrentWeather()
    }
}
