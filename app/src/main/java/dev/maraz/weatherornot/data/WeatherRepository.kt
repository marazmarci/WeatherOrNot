package dev.maraz.weatherornot.data

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.domain.model.WeatherCastData

interface WeatherRepository {

    fun getWeatherCastDataSet(woeid: Long, updateFromRemote: Boolean): LiveData<List<WeatherCastData>?>

    suspend fun refreshFromRemoteAndSaveLocally(woeid: Long): Result<Unit>

}