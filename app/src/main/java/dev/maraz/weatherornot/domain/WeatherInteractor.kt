package dev.maraz.weatherornot.domain

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.domain.model.WeatherCastDataSet

interface WeatherInteractor {

    fun getCurrentWeather(): LiveData<Result<WeatherCastDataSet>>

    suspend fun refreshFromNetwork(): Result<Unit>

}