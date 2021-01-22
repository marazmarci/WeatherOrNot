package dev.maraz.weatherornot.data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherCastData(
    val temperature: CelsiusTemperature,
    val weatherState: WeatherState,
    val applicableDate: LocalDate,
    val timestampUpdated: LocalDateTime
)