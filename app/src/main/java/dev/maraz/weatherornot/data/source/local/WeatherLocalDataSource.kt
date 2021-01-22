package dev.maraz.weatherornot.data.source.local

import dev.maraz.weatherornot.data.source.local.model.DbWeatherCastData
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {

    fun getWeatherCastData(woeid: Long): Flow<List<DbWeatherCastData>>

    suspend fun saveWeatherCastData(weatherCastData: List<DbWeatherCastData>)

    suspend fun deleteAllWeatherCastData()

}