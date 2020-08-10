package com.example.forecastapp.data.repo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.forecastapp.data.db.dao.CurrnetWeatherDao
import com.example.forecastapp.data.db.entity.CurrentWeatherEntry
import com.example.forecastapp.data.network.WeatherNetworkDataSource
import com.example.forecastapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class ForecastRepoImpl(
    private val currentWeatherDao:CurrnetWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
    ) : ForecastRepo {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{newCurrentWeather->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            return@withContext currentWeatherDao.getWeather()
        }
    }
    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        CoroutineScope(IO).launch {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initWeatherData(){
        if (isFetchedCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }
    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "London",
            "en"
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchedCurrentNeeded(lastFetchTime:ZonedDateTime):Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}