package dev.maraz.weatherornot.ui.weather

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import dev.maraz.weatherornot.R
import dev.maraz.weatherornot.ui.AbstractFragment
import dev.maraz.weatherornot.ui.WeatherIconUrls
import kotlinx.coroutines.*
import java.text.DecimalFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@AndroidEntryPoint
class WeatherFragment : AbstractFragment<WeatherViewModel>(WeatherViewModel::class) {

    private val temperatureDecimalFormat = DecimalFormat("#.#")

    override fun getViewResource() = R.layout.weather_fragment

    private val tvTemperature get() = view?.findViewById<TextView>(R.id.tvTemperature)!!
    private val tvWeatherStateName get() = view?.findViewById<TextView>(R.id.tvWeatherStateName)!!
    private val tvLocationName get() = view?.findViewById<TextView>(R.id.tvLocationName)!!
    private val tvDataAge get() = view?.findViewById<TextView>(R.id.tvDataAge)!!
    private val ivWeather get() = view?.findViewById<ImageView>(R.id.ivWeather)!!
    private val swipeRefreshLayout get() = view?.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)!!

    private var dataAgeUpdaterJob: Job? = null

    override fun onStart() {
        super.onStart()
        viewModel.weatherData.observe { dataSet ->
            dataSet?.apply {
                tvTemperature.text = resources.getString(
                    R.string.celsius_template,
                    temperatureDecimalFormat.format(celsiusTemperature)
                )

                tvWeatherStateName.text = weatherState.name

                tvLocationName.text = locationName

                lifecycleScope.launch {
                    dataAgeUpdaterJob?.cancel()
                    dataAgeUpdaterJob = coroutineContext.job
                    while (isActive) {
                        val now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
                        val update = timestampUpdated.truncatedTo(ChronoUnit.SECONDS)
                        val dataAge = Duration.between(update, now)
                        tvDataAge.text = dataAge.toStringPretty()
                        delay(1000)
                    }
                }

                val weatherIconUri = WeatherIconUrls[weatherState]
                ivWeather.load(weatherIconUri)

            } // ?: TODO("error state")
        }
        viewModel.isLoading.observe(swipeRefreshLayout::setRefreshing)
        viewModel.load()
    }

    private fun Duration.toStringPretty() =
        if (seconds < 5L)
            getString(R.string.updated_just_now)
        else
            toString()
                .substring(2)
                .replace("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase(Locale.ROOT)
                .replace("h", "h ")
                .replace("m", "m ")
                .let {
                    resources.getString(R.string.updated_time_ago_template, it)
                }
                .replace("  ", " ")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

}