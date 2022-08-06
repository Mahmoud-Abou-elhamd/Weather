package com.example.android.weather

import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.weather.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val weatherApiClient by lazy { WeatherApi() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getFeed()
    }

    private fun getFeed() {
        weatherApiClient.makeApiRequest().enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { jsonString ->
                    val result = Gson().fromJson(jsonString,WeatherResponse::class.java)
                    runOnUiThread {
                        val updatedAt = result.dt
                        val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                        binding.updatedAt.text = updatedAtText
                        binding.status.text = result.weather[0].description
                        binding.temp.text = result.main.temp.toString() + "°C"
                        binding.minTemp.text = "Min Temp: " + result.main.temp_min.toString() + "°C"
                        binding.maxTemp.text = "Max Temp: " + result.main.temp_max.toString() + "°C"
                        val sunrise = result.sys.sunrise
                        val sunset = result.sys.sunset
                        binding.sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                        binding.sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
                        binding.wind.text = result.wind.speed.toString()
                        binding.humidity.text = result.main.humidity.toString()
                        binding.pressure.text = result.main.pressure.toString()
                        binding.visibility.text = result.visibility.toString()
                    }
                }
            }
        })
    }
}