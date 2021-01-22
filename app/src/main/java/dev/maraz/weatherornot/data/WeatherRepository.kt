package dev.maraz.weatherornot.data

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.domain.model.WeatherCastDataSet

interface WeatherRepository {

    fun getCurrentWeather(): LiveData<Result<WeatherCastDataSet>>

    suspend fun refreshFromNetwork(woeid: Long): Result<Unit>

}