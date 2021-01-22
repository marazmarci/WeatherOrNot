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

    override fun getCurrentWeather(updateFromRemote: Boolean): LiveData<Result<WeatherCastDataSet>> {
        // TODO take time into account (forecasts)
        return weatherRepository.getWeatherCastDataSet(woeid, updateFromRemote)
    }

    override suspend fun refreshFromNetwork(): Result<Unit> {
        return weatherRepository.refreshFromRemoteAndSaveLocally(woeid)
    }

}