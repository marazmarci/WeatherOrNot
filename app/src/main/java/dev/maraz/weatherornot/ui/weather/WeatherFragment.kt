package dev.maraz.weatherornot.ui.weather

import android.os.Bundle
import android.view.View
import android.widget.TextView
import dagger.hilt.android.AndroidEntryPoint
import dev.maraz.weatherornot.R
import dev.maraz.weatherornot.ui.AbstractFragment
import java.text.DecimalFormat

@AndroidEntryPoint
class WeatherFragment : AbstractFragment<WeatherViewModel>(WeatherViewModel::class) {

    private val temperatureDecimalFormat = DecimalFormat("#.##")

    override fun getViewResource() = R.layout.weather_fragment

    private val tvMessage get() = view?.findViewById<TextView>(R.id.tvMessage)

    override fun onStart() {
        super.onStart()
        viewModel.weatherData.observe { result ->
            tvMessage?.text = result.getOrNull()?.castData?.firstOrNull()?.let { todaysWeather ->
                val tempStr = temperatureDecimalFormat.format(todaysWeather.celsiusTemperature)
                "$tempStr °C"
            } ?: "error"
        }
        viewModel.refresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvMessage?.setOnClickListener {
            viewModel.refresh()
        }
    }

}