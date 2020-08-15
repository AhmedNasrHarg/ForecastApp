package com.example.forecastapp.ui.weather.current

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.forecastapp.R
import com.example.forecastapp.data.network.ApixuWeatherApiService
import com.example.forecastapp.data.network.ConnectivityInterceptor
import com.example.forecastapp.data.network.ConnectivityInterceptorImpl
import com.example.forecastapp.data.network.WeatherNetworkDataSourceImpl
import com.example.forecastapp.data.network.response.CurrentWeatherResponse
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
            Log.i("ffff","Nasr1")
            Log.i("ffff",""+it+" me")
            if(it==null)return@Observer
            textView.text = it.toString()
            Log.i("ffff","Nasr2")
        })
    }

}
