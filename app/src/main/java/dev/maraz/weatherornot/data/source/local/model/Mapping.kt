package dev.maraz.weatherornot.data.source.local.model

import dev.maraz.weatherornot.domain.model.WeatherCastData
import dev.maraz.weatherornot.domain.model.WeatherState
import java.time.LocalDate
import java.time.LocalDateTime

fun List<WeatherCastData>.toDbModels() = mapIndexed { idx, it ->
    it.toDbModel(idx)
}

fun WeatherCastData.toDbModel(idx: Int) = DbWeatherCastData(
    id = idx + 1L,
    celsiusTemperature = celsiusTemperature,
    weatherStateAbbreviation = weatherState.abbreviation,
    weatherStateName = weatherState.name,
    locationName = locationName,
    woeid = woeid,
    applicableDate = applicableDate.toString(),
    timestampUpdated = timestampUpdated.toString()
)

fun List<DbWeatherCastData>.toDomainModels() =
    takeIf {
        isNotEmpty()
    }?.map {
        it.toDomainModels()
    }

fun DbWeatherCastData.toDomainModels() = WeatherCastData(
    celsiusTemperature = celsiusTemperature,
    weatherState = WeatherState(weatherStateAbbreviation, weatherStateName),
    applicableDate = LocalDate.parse(applicableDate),
    timestampUpdated = LocalDateTime.parse(timestampUpdated),
    locationName = locationName,
    woeid = woeid
)