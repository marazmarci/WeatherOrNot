package dev.maraz.weatherornot.data

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.domain.model.WeatherCastDataSet

interface WeatherRepository {

    fun getWeatherCastDataSet(woeid: Long, updateFromRemote: Boolean): LiveData<Result<WeatherCastDataSet>>

    suspend fun refreshFromRemoteAndSaveLocally(woeid: Long): Result<Unit>

}