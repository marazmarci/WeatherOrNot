package dev.maraz.weatherornot.ui.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.maraz.weatherornot.domain.WeatherInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(
    private val weatherInteractor: WeatherInteractor
) : ViewModel() {

    val latestWeatherData by lazy {
        weatherInteractor.getCurrentWeather(false)
    }

    private val _isLoading = MutableStateFlow(false)

    val isLoading get() = _isLoading.asLiveData()

    private var isFirstLoadCall = true

    fun load() {
        if (isFirstLoadCall) {
            isFirstLoadCall = false
            refresh()
        }
    }

    fun refresh() = viewModelScope.launch {
        if (!_isLoading.value) {
            _isLoading.value = true
            weatherInteractor.refreshFromNetwork() // TODO check success
            _isLoading.value = false
        }
    }

}