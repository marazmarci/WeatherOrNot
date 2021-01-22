package dev.maraz.weatherornot.data.source.local

import dev.maraz.weatherornot.data.source.local.dao.WeatherDao
import dev.maraz.weatherornot.data.source.local.model.DbWeatherCastData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultWeatherLocalDataSource @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {

    override fun getWeatherCastData(woeid: Long) =
        weatherDao.getWeatherCastData(woeid)

    override suspend fun saveWeatherCastData(weatherCastData: List<DbWeatherCastData>) =
        weatherDao.insertAll(weatherCastData)

    override suspend fun deleteAllWeatherCastData() =
        weatherDao.deleteAll()

}