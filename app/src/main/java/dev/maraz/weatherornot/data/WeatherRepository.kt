package dev.maraz.weatherornot.data

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.domain.model.WeatherCastData
import kotlinx.coroutines.flow.StateFlow

interface WeatherRepository {

    fun getCurrentWeather(): LiveData<List<WeatherCastData>?>

    suspend fun refreshFromRemoteAndSaveLocally(forceRefresh: Boolean)

    val isLoadingFromNetwork: StateFlow<Boolean>

}