package dev.maraz.weatherornot.domain.model

data class WeatherCastDataSet(
    val castData: List<WeatherCastData>,
    val locationName: String,
    val woeid: Long
)