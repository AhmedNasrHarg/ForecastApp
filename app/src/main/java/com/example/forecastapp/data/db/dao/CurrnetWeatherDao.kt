package com.example.forecastapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastapp.data.db.entity.CurrentWeatherEntry

@Dao
interface CurrnetWeatherDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id = 0")
    fun getWeather():LiveData<CurrentWeatherEntry>
}
