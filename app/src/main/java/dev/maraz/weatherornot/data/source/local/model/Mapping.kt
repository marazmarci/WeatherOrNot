package dev.maraz.weatherornot.data.source.local.model

import dev.maraz.weatherornot.domain.model.WeatherCastData
import dev.maraz.weatherornot.domain.model.WeatherCastDataSet
import dev.maraz.weatherornot.domain.model.WeatherState
import java.time.LocalDate
import java.time.LocalDateTime

fun WeatherCastDataSet.toDbModels() = castData.mapIndexed { i, it ->
    with(it) {
        DbWeatherCastData(
            id = i + 1L,
            celsiusTemperature = celsiusTemperature,
            weatherStateAbbreviation = weatherState.abbreviation,
            weatherStateName = weatherState.name,
            locationName = locationName,
            woeid = woeid,
            applicableDate = applicableDate.toString(),
            timestampUpdated = timestampUpdated.toString()
        )
    }
}

fun List<DbWeatherCastData>.toDomainModel(): WeatherCastDataSet? {
    if (isEmpty())
        return null
    val locationName = first().locationName
    val woeid = first().woeid
    return WeatherCastDataSet(
        castData = map {
            with(it) {
                WeatherCastData(
                    celsiusTemperature = celsiusTemperature,
                    weatherState = WeatherState(weatherStateAbbreviation, weatherStateName),
                    applicableDate = LocalDate.parse(applicableDate),
                    timestampUpdated = LocalDateTime.parse(timestampUpdated)
                )
            }
        },
        locationName = locationName,
        woeid = woeid
    )
}