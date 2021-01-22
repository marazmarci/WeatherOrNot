package dev.maraz.weatherornot.ui.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.maraz.weatherornot.domain.WeatherInteractor
import dev.maraz.weatherornot.ui.weather.WeatherViewModel.RefreshingState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(
    private val weatherInteractor: WeatherInteractor
) : ViewModel() {

    val latestWeatherData by lazy { weatherInteractor.getCurrentWeather() }

    private val refreshingState = MutableStateFlow(NOT_REFRESHING)

    val isRefreshingInitiatedByUser
        get() = refreshingState.map { it.isManual }.asLiveData()

    fun load() {
        refresh(isManual = false)
    }

    fun refresh() {
        refresh(isManual = true)
    }

    private fun refresh(isManual: Boolean) = viewModelScope.launch {
        if (refreshingState.value.isRefreshing) {
            if (isManual)
                refreshingState.value = REFRESHING_MANUAL
        } else {
            refreshingState.value =
                if (isManual) REFRESHING_MANUAL else REFRESHING
            weatherInteractor.refreshFromNetwork() // TODO check success
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