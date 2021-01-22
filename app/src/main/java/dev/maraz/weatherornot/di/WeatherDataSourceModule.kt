package dev.maraz.weatherornot.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.maraz.weatherornot.data.source.local.DefaultWeatherLocalDataSource
import dev.maraz.weatherornot.data.source.local.WeatherLocalDataSource
import dev.maraz.weatherornot.data.source.remote.DefaultWeatherRemoteDataSource
import dev.maraz.weatherornot.data.source.remote.WeatherRemoteDataSource

@InstallIn(ApplicationComponent::class)
@Module
class WeatherDataSourceModule {

    @Provides
    fun provideWeatherRemoteDataSource(defaultWeatherRemoteDataSource: DefaultWeatherRemoteDataSource): WeatherRemoteDataSource =
        defaultWeatherRemoteDataSource

    @Provides
    fun provideWeatherLocalDataSource(defaultWeatherLocalDataSource: DefaultWeatherLocalDataSource): WeatherLocalDataSource =
        defaultWeatherLocalDataSource

}