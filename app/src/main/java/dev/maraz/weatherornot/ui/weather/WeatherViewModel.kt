package dev.maraz.weatherornot.ui.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dev.maraz.weatherornot.data.WeatherRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val woeid = 804365L // TODO don't hardcode woeid here

    val weatherData by lazy { weatherRepository.getCurrentWeather() }

    fun refresh() = viewModelScope.launch {
        weatherRepository.refreshFromNetwork(woeid)
    }

}