package dev.maraz.weatherornot.data.source.remote.model

import dev.maraz.weatherornot.domain.model.WeatherCastData
import dev.maraz.weatherornot.domain.model.WeatherCastDataSet
import dev.maraz.weatherornot.domain.model.WeatherState
import java.time.LocalDate
import java.time.LocalDateTime

fun GetWeatherByLocationResponse.toDomainModel(
    now: LocalDateTime
) = WeatherCastDataSet(
    castData = consolidatedWeather.map {
        WeatherCastData(
            celsiusTemperature = it.theTemp,
            weatherState = WeatherState(it.weatherStateAbbr, it.weatherStateName),
            applicableDate = LocalDate.parse(it.applicableDate),
            timestampUpdated = now
        )
    },
    locationName = locationName,
    woeid = woeid
)