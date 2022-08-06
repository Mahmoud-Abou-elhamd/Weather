package com.example.android.weather

data class WeatherResponse(
    val weather: List<Status>,
    val main: WeatherInfo,
    val visibility: Int,
    val wind: WindInfo,
    val dt: Long,
    val sys: SunTimes
)
