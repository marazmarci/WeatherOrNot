package dev.maraz.weatherornot.ui.weather

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import dev.maraz.weatherornot.R
import dev.maraz.weatherornot.ui.AbstractFragment
import java.text.DecimalFormat

@AndroidEntryPoint
class WeatherFragment : AbstractFragment<WeatherViewModel>(WeatherViewModel::class) {

    private val temperatureDecimalFormat = DecimalFormat("#.##")

    override fun getViewResource() = R.layout.weather_fragment

    private val tvTemperature get() = view?.findViewById<TextView>(R.id.tvTemperature)!!
    private val swipeRefreshLayout get() = view?.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)!!

    override fun onStart() {
        super.onStart()
        viewModel.weatherData.observe { result ->
            tvTemperature.text = result.getOrNull()?.castData?.firstOrNull()?.let { todaysWeather ->
                val tempStr = temperatureDecimalFormat.format(todaysWeather.celsiusTemperature)
                "$tempStr °C"
            } ?: "error"
        }
        viewModel.isRefreshingInitiatedByUser.observe(swipeRefreshLayout::setRefreshing)
        viewModel.refresh(isManual = false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh(isManual = true)
        }
    }

}