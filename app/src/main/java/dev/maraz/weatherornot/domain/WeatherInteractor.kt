package dev.maraz.weatherornot.domain

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.domain.model.WeatherCastData

interface WeatherInteractor {

    fun getCurrentWeather(updateFromRemote: Boolean): LiveData<List<WeatherCastData>?>

    suspend fun refreshFromNetwork(): Result<Unit>

}