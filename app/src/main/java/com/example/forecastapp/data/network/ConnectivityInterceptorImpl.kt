package com.example.forecastapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.forecastapp.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(context:Context) : ConnectivityInterceptor {
    private val appContext =context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())

    }
    private  fun isOnline():Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager
        val netwokInfo = connectivityManager.activeNetworkInfo
        return netwokInfo !=null && netwokInfo.isConnected
    }
}