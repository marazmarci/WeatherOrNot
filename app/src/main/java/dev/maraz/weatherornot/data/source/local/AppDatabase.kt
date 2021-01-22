package dev.maraz.weatherornot.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.maraz.weatherornot.data.source.local.dao.WeatherDao
import dev.maraz.weatherornot.data.source.local.model.DbWeatherCastData

@Database(
    entities = [
        DbWeatherCastData::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {

        fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }

        private const val DATABASE_NAME = "weather_or_not"
    }

}