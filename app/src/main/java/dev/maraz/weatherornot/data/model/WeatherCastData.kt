package dev.maraz.weatherornot.data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherCastData(
    val celsiusTemperature: Double,
    val weatherState: WeatherState,
    val applicableDate: LocalDate,
    val timestampUpdated: LocalDateTime
)