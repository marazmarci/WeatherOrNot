package dev.maraz.weatherornot.data.source.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetWeatherByLocationResponse(
    @SerialName("consolidated_weather")
    val consolidatedWeather: List<ConsolidatedWeather>,
    @SerialName("time")
    val time: String,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_name")
    val timezoneName: String,
    @SerialName("title")
    val locationName: String,
    @SerialName("woeid")
    val woeid: Long
)

@Serializable
data class ConsolidatedWeather(
    @SerialName("applicable_date")
    val applicableDate: String,
    @SerialName("created")
    val created: String,
    @SerialName("id")
    val id: Long,
    @SerialName("max_temp")
    val maxTemp: Double,
    @SerialName("min_temp")
    val minTemp: Double,
    @SerialName("predictability")
    val predictability: Int,
    @SerialName("the_temp")
    val theTemp: Double,
    @SerialName("weather_state_abbr")
    val weatherStateAbbr: String,
    @SerialName("weather_state_name")
    val weatherStateName: String,
)