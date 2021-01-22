package dev.maraz.weatherornot.domain

import dev.maraz.weatherornot.data.WeatherRepository
import dev.maraz.weatherornot.domain.model.WeatherCastData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultWeatherInteractor @Inject constructor(
    private val weatherRepository: WeatherRepository
) : WeatherInteractor {

    override val isLoadingFromNetwork get() = weatherRepository.isLoadingFromNetwork
    override val networkErrors get() = weatherRepository.networkErrors

    override fun getCurrentWeather(): Flow<WeatherCastData> {
        return weatherRepository.getCurrentWeather()
            .map { list ->
                val today = LocalDate.now()
                val idx = list.indexOfFirst {
                    it.applicableDate == today
                }.takeIf { it != -1 } ?: 0
                list[idx].copy(isForecast = idx > 0)
            }
    }

    override suspend fun refreshFromNetworkIfCacheHasExpired(forceRefresh: Boolean) {
        weatherRepository.refreshFromNetworkIfCacheHasExpired(forceRefresh)
    }

}