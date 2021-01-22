package dev.maraz.weatherornot.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dev.maraz.weatherornot.data.model.WeatherCastData
import dev.maraz.weatherornot.data.model.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class DefaultWeatherRepository @Inject constructor(

) : WeatherRepository {

    private val weather = MutableStateFlow<WeatherCastData?>(null)

    override fun getCurrentWeather(): LiveData<WeatherCastData> {
        return liveData {
            weather.filterNotNull().collect {
                emit(it)
            }
        }
    }

    override suspend fun refresh() {
        weather.value = WeatherCastData(
            Random.nextDouble(-10.0, 35.0),
            weatherState = WeatherState("lr", "Light Rain"),
            LocalDate.now(),
            LocalDateTime.now(),
        )
    }

}