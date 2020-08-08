package com.example.forecastapp.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastapp.data.network.response.CurrentWeatherResponse
import com.example.forecastapp.internal.NoConnectivityException
import java.lang.Exception

class WeatherNetworkDataSourceImpl(private val apixuWeatherApiService: ApixuWeatherApiService) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, langCode: String) {
        try{
            val fetchCurrentWeather = apixuWeatherApiService
                .getCurrentWeather(location,langCode)
                .await()
            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        }catch (e: NoConnectivityException){
            println("No Connection ")
        }
    }
}