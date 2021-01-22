package dev.maraz.weatherornot.ui.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dev.maraz.weatherornot.data.WeatherRepository
import dev.maraz.weatherornot.ui.weather.WeatherViewModel.RefreshingState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val woeid = 804365L // TODO don't hardcode woeid here

    val weatherData by lazy { weatherRepository.getCurrentWeather() }

    private val refreshingState = MutableStateFlow(NOT_REFRESHING)

    val isRefreshingInitiatedByUser
        get() = refreshingState.map { it.isManual }.asLiveData()

    fun refresh(isManual: Boolean) = viewModelScope.launch {
        if (refreshingState.value.isRefreshing) {
            if (isManual)
                refreshingState.value = REFRESHING_MANUAL
        } else {
            refreshingState.value =
                if (isManual) REFRESHING_MANUAL else REFRESHING
            weatherRepository.refreshFromNetwork(woeid) // TODO check success
            refreshingState.value = NOT_REFRESHING
        }
    }

    private enum class RefreshingState(
        val isRefreshing: Boolean,
        val isManual: Boolean
    ) {
        NOT_REFRESHING(false, false),
        REFRESHING(true, false),
        REFRESHING_MANUAL(true, true)
    }

}