package dev.maraz.weatherornot.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherCastData(
    val celsiusTemperature: Double,
    val weatherState: WeatherState,
    val applicableDate: LocalDate,
    val timestampUpdated: LocalDateTime,
    val locationName: String,
    val woeid: Long,
    val isForecast: Boolean = false
)