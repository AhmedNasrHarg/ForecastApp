package com.example.forecastapp.data.db.forecast_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forecastapp.data.db.dao.CurrnetWeatherDao
import com.example.forecastapp.data.db.entity.CurrentWeatherEntry

@Database(entities = arrayOf(CurrentWeatherEntry::class),version = 1)
abstract class ForecastDatabase:RoomDatabase(){
    abstract fun currnetWeatherDao(): CurrnetWeatherDao

    companion object{
        @Volatile private var instance: ForecastDatabase?=null
        private val LOCK = Any()
        operator fun invoke(contex:Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(contex).also { instance = it }
        }
      private  fun buildDatabase(contex:Context) = Room.databaseBuilder(
          contex,
          ForecastDatabase::class.java, "forecast.db"
        ).build()
    }
}