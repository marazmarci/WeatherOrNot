package dev.maraz.weatherornot.data

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.data.model.WeatherCastData

interface WeatherRepository {

    fun getCurrentWeather(): LiveData<WeatherCastData>

    suspend fun refresh()

}