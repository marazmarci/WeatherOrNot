package dev.maraz.weatherornot.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class WeatherViewModel : ViewModel() {

    private val weatherIdx = MutableLiveData(0)
    val weatherString: LiveData<String> = weatherIdx.map { dummyWeatherStrings[it % dummyWeatherStrings.size] }

    fun refresh() {
        weatherIdx.value = weatherIdx.value!! + 1
    }

}

private val dummyWeatherStrings = listOf("sunny", "raining", "snowing", "cloudy", "windy")