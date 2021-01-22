package dev.maraz.weatherornot.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cast_data")
data class DbWeatherCastData(
    val celsiusTemperature: Double,
    val weatherStateAbbreviation: String,
    val weatherStateName: String,
    val locationName: String,
    val woeid: Long,
    val applicableDate: String,
    val timestampUpdated: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)