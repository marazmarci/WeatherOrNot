package dev.maraz.weatherornot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.maraz.weatherornot.ui.weather.WeatherFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}