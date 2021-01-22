package dev.maraz.weatherornot.data.source.remote

import dev.maraz.weatherornot.data.source.remote.model.GetWeatherByLocationResponse

interface WeatherRemoteDataSource {

    suspend fun getWeatherByLocation(woeid: Long): GetWeatherByLocationResponse

}