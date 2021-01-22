package dev.maraz.weatherornot.ui.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.maraz.weatherornot.domain.WeatherInteractor
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(
    private val weatherInteractor: WeatherInteractor
) : ViewModel() {

    val weatherData by lazy {
        weatherInteractor.getCurrentWeather().asLiveData()
    }

    val isLoading get() = weatherInteractor.isLoadingFromNetwork.asLiveData()

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