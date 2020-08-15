package com.example.forecastapp

import android.app.Application
import com.example.forecastapp.data.db.forecast_db.ForecastDatabase
import com.example.forecastapp.data.network.*
import com.example.forecastapp.data.repo.ForecastRepo
import com.example.forecastapp.data.repo.ForecastRepoImpl
import com.example.forecastapp.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApp: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApp))
        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currnetWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepo>() with singleton { ForecastRepoImpl(instance(),instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}