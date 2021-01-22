package dev.maraz.weatherornot.data

import dev.maraz.weatherornot.data.source.local.WeatherLocalDataSource
import dev.maraz.weatherornot.data.source.local.model.toDbModels
import dev.maraz.weatherornot.data.source.local.model.toDomainModels
import dev.maraz.weatherornot.data.source.remote.WeatherRemoteDataSource
import dev.maraz.weatherornot.data.source.remote.model.toDomainModel
import dev.maraz.weatherornot.domain.model.WeatherCastData
import kotlinx.coroutines.flow.*
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

    override val networkErrors = MutableSharedFlow<Throwable>()

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
            weatherRemoteDataSource.getWeatherByLocation(woeid)
                .map { response ->
                    val now = LocalDateTime.now()
                    response.toDomainModel(now)
                }.onSuccess {
                    with(weatherLocalDataSource) {
                        deleteAllWeatherCastData()
                        saveWeatherCastData(it.toDbModels())
                    }
                }.onFailure {
                    networkErrors.emit(it)
                }
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