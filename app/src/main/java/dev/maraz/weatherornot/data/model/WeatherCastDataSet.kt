package dev.maraz.weatherornot.data.model

data class WeatherCastDataSet(
    val castData: List<WeatherCastData>,
    val locationName: String,
    val woeid: Long
)