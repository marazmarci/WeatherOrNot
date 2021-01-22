package dev.maraz.weatherornot.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dev.maraz.weatherornot.data.model.WeatherCastData
import dev.maraz.weatherornot.data.model.WeatherCastDataSet
import dev.maraz.weatherornot.data.model.WeatherState
import dev.maraz.weatherornot.data.source.remote.WeatherRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import java.time.LocalDate
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
        val result = weatherRemoteDataSource.getWeatherByLocation(804365)
        return result.map { response ->
            with(response) {
                val now = LocalDateTime.now()
                WeatherCastDataSet(
                    castData = consolidatedWeather.map {
                        WeatherCastData(
                            celsiusTemperature = it.theTemp,
                            weatherState = WeatherState(it.weatherStateAbbr, it.weatherStateName),
                            applicableDate = LocalDate.parse(it.applicableDate),
                            timestampUpdated = now
                        )
                    },
                    locationName = locationName,
                    woeid = woeid
                )
            }
        }
    }

    override suspend fun refreshFromNetwork(woeid: Long): Boolean {
        val result = fetchFromNetwork(woeid)
        weather.value = result
        return result.isSuccess
    }

}