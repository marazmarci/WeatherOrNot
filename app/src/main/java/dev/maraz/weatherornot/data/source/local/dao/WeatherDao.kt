package dev.maraz.weatherornot.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.maraz.weatherornot.data.source.local.model.DbWeatherCastData

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_cast_data WHERE woeid = :woeid ORDER BY id ASC")
    fun getWeatherCastData(woeid: Long): LiveData<List<DbWeatherCastData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weatherCastData: List<DbWeatherCastData>)

    @Query("DELETE FROM weather_cast_data")
    suspend fun deleteAll()

}