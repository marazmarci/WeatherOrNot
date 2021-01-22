package dev.maraz.weatherornot.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dev.maraz.weatherornot.domain.model.WeatherCastDataSet
import dev.maraz.weatherornot.data.source.remote.WeatherRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultWeatherRepository @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {

    private val weather = MutableStateFlow<Result<WeatherCastDataSet>?>(null)

    override fun getCurrentWeather(): LiveData<Result<WeatherCastDataSet>> {
        return liveData {
            weather.filterNotNull().collect {
                emit(it)
            }
        }
    }

    private suspend fun fetchFromNetwork(woeid: Long): Result<WeatherCastDataSet> {
        val result = weatherRemoteDataSource.getWeatherByLocation(woeid)
        val now = LocalDateTime.now()
        return result.map { response ->
            response.toDomainModel(now)
        }
    }

    override suspend fun refreshFromNetwork(woeid: Long): Result<Unit> {
        val result = fetchFromNetwork(woeid)
        weather.value = result
        return result.map { }
    }

}