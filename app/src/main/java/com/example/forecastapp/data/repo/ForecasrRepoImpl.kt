package com.example.forecastapp.data.repo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.forecastapp.data.db.dao.CurrnetWeatherDao
import com.example.forecastapp.data.db.entity.CurrentWeatherEntry
import com.example.forecastapp.data.network.WeatherNetworkDataSource
import com.example.forecastapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class ForecastRepoImpl(
    private val currentWeatherDao:CurrnetWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
    ) : ForecastRepo {

    init {
        Log.i("ffff","nonnaaaa")
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{newCurrentWeather->
            persistFetchedCurrentWeather(newCurrentWeather)
            Log.i("ffff","nonn")
        }
        GlobalScope.launch {
            initWeatherData()
        }

    }
    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            return@withContext currentWeatherDao.getWeather()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
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
            "Cairo",
            "en"
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchedCurrentNeeded(lastFetchTime:ZonedDateTime):Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}