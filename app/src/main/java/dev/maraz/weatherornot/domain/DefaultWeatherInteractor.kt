package dev.maraz.weatherornot.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import dev.maraz.weatherornot.data.WeatherRepository
import dev.maraz.weatherornot.domain.model.WeatherCastData
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultWeatherInteractor @Inject constructor(
    private val weatherRepository: WeatherRepository
) : WeatherInteractor {

    override val isLoadingFromNetwork get() = weatherRepository.isLoadingFromNetwork

    override fun getCurrentWeather(): LiveData<WeatherCastData?> {
        return weatherRepository.getCurrentWeather()
            .map {
                // TODO select based on current date
                it?.first()
            }
    }

    override suspend fun refreshFromNetworkIfCacheHasExpired(forceRefresh: Boolean) {
        weatherRepository.refreshFromRemoteAndSaveLocally(forceRefresh)
    }

}