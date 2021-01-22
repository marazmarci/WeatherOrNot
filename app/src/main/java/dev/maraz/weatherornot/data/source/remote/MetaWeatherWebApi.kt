package dev.maraz.weatherornot.data.source.remote

import dev.maraz.weatherornot.data.source.remote.model.GetWeatherByLocationResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MetaWeatherWebApi {

    @GET("location/{woeid}")
    suspend fun getWeatherByLocation(@Path("woeid") woeid: Long): GetWeatherByLocationResponse

}