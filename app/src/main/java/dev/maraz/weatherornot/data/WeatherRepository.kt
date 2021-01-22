package dev.maraz.weatherornot.data

import dev.maraz.weatherornot.domain.model.WeatherCastData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WeatherRepository {

    fun getCurrentWeather(): Flow<List<WeatherCastData>>

    suspend fun refreshFromNetworkIfCacheHasExpired(forceRefresh: Boolean)

    val isLoadingFromNetwork: StateFlow<Boolean>

    val networkErrors: Flow<Throwable>

}