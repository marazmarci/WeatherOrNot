package dev.maraz.weatherornot.data.source.remote

import dev.maraz.weatherornot.data.source.remote.model.GetWeatherByLocationResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultWeatherRemoteDataSource @Inject constructor(
    private val metaWeatherWebApi: MetaWeatherWebApi
) : WeatherRemoteDataSource {

    override suspend fun getWeatherByLocation(woeid: Long): Result<GetWeatherByLocationResponse> =
        runCatching {
            metaWeatherWebApi.getWeatherByLocation(woeid)
        }

}