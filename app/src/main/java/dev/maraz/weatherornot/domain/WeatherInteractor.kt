package dev.maraz.weatherornot.domain

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.domain.model.WeatherCastData
import kotlinx.coroutines.flow.StateFlow

interface WeatherInteractor {

    fun getCurrentWeather(): LiveData<WeatherCastData?>

    suspend fun refreshFromNetworkIfCacheHasExpired(forceRefresh: Boolean)

    val isLoadingFromNetwork: StateFlow<Boolean>

}