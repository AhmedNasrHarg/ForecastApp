package com.example.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.forecastapp.R
import com.example.forecastapp.data.ApixuWeatherApiService
import com.example.forecastapp.data.response.CurrentWeatherResponse
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)

        val apiService=ApixuWeatherApiService()
        CoroutineScope(Main).launch {
            val currentWeatherResponse:CurrentWeatherResponse =  apiService.getCurrentWeather("London").await()
            textView.text = currentWeatherResponse.currentWeatherEntry.toString()//.currentWeatherEntry.weatherDescriptions.toString()
        }
        // TODO: Use the ViewModel
    }

}
