package com.example.weatherkotlin.data

import java.time.LocalDateTime

data class Weather(
    val dataTime: LocalDateTime,
    val currentTemp: Double,
    val maxTemp: Double,
    val minTemp: Double,
    val city: String,
    val description: String,
    val icon: String,
)

data class DayHeading(val dateTime: LocalDateTime)
