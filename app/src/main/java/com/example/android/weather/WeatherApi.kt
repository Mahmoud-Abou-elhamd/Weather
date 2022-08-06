package com.example.android.weather

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

class WeatherApi {
    private val client = OkHttpClient()

    fun makeApiRequest(): Call {
        val request = Request.Builder()
            .url(Constants.BASE_URL)
            .build()

        return client.newCall(request)
    }
}