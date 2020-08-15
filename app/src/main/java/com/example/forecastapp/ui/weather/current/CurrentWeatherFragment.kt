package com.example.forecastapp.ui.weather.current

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.forecastapp.R
import com.example.forecastapp.data.network.ApixuWeatherApiService
import com.example.forecastapp.data.network.ConnectivityInterceptor
import com.example.forecastapp.data.network.ConnectivityInterceptorImpl
import com.example.forecastapp.data.network.WeatherNetworkDataSourceImpl
import com.example.forecastapp.data.network.response.CurrentWeatherResponse
import com.example.forecastapp.internal.glide.GlideApp
import com.example.forecastapp.ui.base.ScoppedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScoppedFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory:CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUI()
    }
    @SuppressLint("FragmentLiveDataObserve")
    private fun bindUI()=launch{
        val currentWeather = viewModel.weather.await()
        Log.i("ffff","Nasr")
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if(it==null)return@Observer

            group_loading.visibility = View.GONE
            updateLocation("Cairo")
            updateDateToToday()
            updateTemperature(it.temperature, it.feelslike)
            updateCondition(it.weatherDescriptions[0])
            updatePrecipitation(it.precip)
            updateWind(it.windDir, it.windSpeed)
            updateVisibility(it.visibility)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("${it.weatherIcons[0]}")
                .into(imageView_condition_icon)
            Log.i("ffff","${it.weatherIcons[0]}")
        })
    }
    
    private fun updateLocation(location:String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun  updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature:Double,feelsLike:Double){
        textView_temperature.text = "$temperature°C"
        textView_feels_like_temperature.text = "Feels Like $feelsLike°C"
    }
    private fun updateCondition(condition:String){
        textView_condition.text = condition
    }
    private fun updatePrecipitation(precipitationVolume:Double){
        textView_precipitation.text = "Precipitation: $precipitationVolume mm"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        textView_wind.text = "Wind: $windDirection, $windSpeed kph"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        textView_visibility.text = "Visibility: $visibilityDistance km"
    }
}
