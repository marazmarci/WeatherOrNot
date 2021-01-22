package dev.maraz.weatherornot.ui.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.maraz.weatherornot.domain.WeatherInteractor
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(
    private val weatherInteractor: WeatherInteractor
) : ViewModel() {

    val weatherData by lazy {
        weatherInteractor.getCurrentWeather().asLiveData()
    }

    val isLoading get() = weatherInteractor.isLoadingFromNetwork.asLiveData()
    val networkErrors get() = weatherInteractor.networkErrors
        .map {
            it.localizedMessage ?: it::class.simpleName
        }.asLiveData()

    private var isFirstLoadCall = true

    fun load() {
        if (isFirstLoadCall) {
            isFirstLoadCall = false
            viewModelScope.launch {
                weatherInteractor.refreshFromNetworkIfCacheHasExpired(forceRefresh = false)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            weatherInteractor.refreshFromNetworkIfCacheHasExpired(forceRefresh = true)
        }
    }

}