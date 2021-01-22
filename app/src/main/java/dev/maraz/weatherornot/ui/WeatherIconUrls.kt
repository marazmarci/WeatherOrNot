package dev.maraz.weatherornot.ui

import android.net.Uri
import dev.maraz.weatherornot.domain.model.WeatherState

object WeatherIconUrls {

    private const val urlTemplate = "https://www.metaweather.com/static/img/weather/png/%s.png"

    operator fun get(weatherState: WeatherState): Uri {
        val url = urlTemplate.format(weatherState.abbreviation)
        return Uri.parse(url)
    }

}