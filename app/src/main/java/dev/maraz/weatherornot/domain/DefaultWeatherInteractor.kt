package dev.maraz.weatherornot.domain

import androidx.lifecycle.LiveData
import dev.maraz.weatherornot.data.WeatherRepository
import dev.maraz.weatherornot.domain.model.WeatherCastDataSet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultWeatherInteractor @Inject constructor(
    private val weatherRepository: WeatherRepository
) : WeatherInteractor {

    private val woeid = 804365L // TODO don't hardcode woeid here

    override fun getCurrentWeather(): LiveData<Result<WeatherCastDataSet>> {
        return weatherRepository.getCurrentWeather()
    }

    override suspend fun refreshFromNetwork(): Result<Unit> {
        return weatherRepository.refreshFromNetwork(woeid)
    }

}