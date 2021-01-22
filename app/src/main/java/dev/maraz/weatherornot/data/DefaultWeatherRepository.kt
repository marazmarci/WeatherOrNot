package dev.maraz.weatherornot.data

import dev.maraz.weatherornot.data.source.local.WeatherLocalDataSource
import dev.maraz.weatherornot.data.source.local.model.toDbModels
import dev.maraz.weatherornot.data.source.local.model.toDomainModels
import dev.maraz.weatherornot.data.source.remote.WeatherRemoteDataSource
import dev.maraz.weatherornot.data.source.remote.model.toDomainModel
import dev.maraz.weatherornot.domain.model.WeatherCastData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultWeatherRepository @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource
) : WeatherRepository {

    companion object {
        private const val woeid = 804365L
        private val cacheValidAgeThreshold = Duration.ofMinutes(1)
    }

    override val isLoadingFromNetwork = MutableStateFlow(false)

    // TODO publish network errors

    private var latestLocalData: List<WeatherCastData>? = null

    override fun getCurrentWeather() =
        getCurrentWeatherNullable().filterNotNull()

    private fun getCurrentWeatherNullable() =
        weatherLocalDataSource.getWeatherCastData(woeid)
            .map {
                val domainModel = it.toDomainModels()
                latestLocalData = domainModel
                domainModel
            }

    override suspend fun refreshFromNetworkIfCacheHasExpired(forceRefresh: Boolean) {
        if (forceRefresh || hasCacheExpired()) {
            val wasLoading = !isLoadingFromNetwork.compareAndSet(expect = false, update = true)
            if (wasLoading)
                return
            val result = weatherRemoteDataSource.getWeatherByLocation(woeid)
                .map { response ->
                    val now = LocalDateTime.now()
                    response.toDomainModel(now)
                }.onSuccess {
                    weatherLocalDataSource.deleteAllWeatherCastData()
                    weatherLocalDataSource.saveWeatherCastData(it.toDbModels())
                }
            // TODO handle network errors
            isLoadingFromNetwork.value = false
        }
    }

    private suspend inline fun hasCacheExpired(): Boolean {
        if (latestLocalData == null)
            latestLocalData = getCurrentWeatherNullable().firstOrNull()
        return latestLocalData?.firstOrNull()?.run {
            val now = LocalDateTime.now()
            val age = Duration.between(timestampUpdated, now)
            age > cacheValidAgeThreshold
        } ?: true
    }

}