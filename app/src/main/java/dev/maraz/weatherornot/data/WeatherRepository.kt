package dev.maraz.weatherornot.data

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.data.model.WeatherCastDataSet

interface WeatherRepository {

    fun getCurrentWeather(): LiveData<WeatherCastDataSet>

    suspend fun refreshFromNetwork(woeid: Long): Boolean

}