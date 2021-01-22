package dev.maraz.weatherornot.domain

import dev.maraz.weatherornot.domain.model.WeatherCastData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WeatherInteractor {

    fun getCurrentWeather(): Flow<WeatherCastData>

    suspend fun refreshFromNetworkIfCacheHasExpired(forceRefresh: Boolean)

    val isLoadingFromNetwork: StateFlow<Boolean>

}