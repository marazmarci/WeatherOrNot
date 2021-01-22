package dev.maraz.weatherornot.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import dev.maraz.weatherornot.data.source.local.WeatherLocalDataSource
import dev.maraz.weatherornot.data.source.local.model.toDbModels
import dev.maraz.weatherornot.data.source.local.model.toDomainModels
import dev.maraz.weatherornot.data.source.remote.WeatherRemoteDataSource
import dev.maraz.weatherornot.data.source.remote.model.toDomainModel
import dev.maraz.weatherornot.domain.model.WeatherCastData
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultWeatherRepository @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource
) : WeatherRepository {

    override fun getWeatherCastDataSet(
        woeid: Long,
        updateFromRemote: Boolean
    ): LiveData<List<WeatherCastData>?> {
        return liveData {
            val localData = weatherLocalDataSource.getWeatherCastData(woeid)
                .map {
                    it.toDomainModels()
                }
            emitSource(localData)
            refreshFromRemoteAndSaveLocally(woeid)
        }
    }

    override suspend fun refreshFromRemoteAndSaveLocally(woeid: Long): Result<Unit> {
        val result = weatherRemoteDataSource.getWeatherByLocation(woeid)
            .map { response ->
                response.toDomainModel(LocalDateTime.now())
            }
        return result.onSuccess {
            weatherLocalDataSource.deleteAllWeatherCastData()
            weatherLocalDataSource.saveWeatherCastData(it.toDbModels())
        }.map { /* Unit */ }
    }

}