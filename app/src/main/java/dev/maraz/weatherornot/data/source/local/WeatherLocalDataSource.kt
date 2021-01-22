package dev.maraz.weatherornot.data.source.local

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.data.source.local.model.DbWeatherCastData

interface WeatherLocalDataSource {

    fun getWeatherCastData(woeid: Long): LiveData<List<DbWeatherCastData>>

    suspend fun saveWeatherCastData(weatherCastData: List<DbWeatherCastData>)

    suspend fun deleteAllWeatherCastData()

}