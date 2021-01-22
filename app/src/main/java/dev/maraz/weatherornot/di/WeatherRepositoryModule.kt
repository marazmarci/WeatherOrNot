package dev.maraz.weatherornot.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.maraz.weatherornot.data.DefaultWeatherRepository
import dev.maraz.weatherornot.data.WeatherRepository

@InstallIn(ApplicationComponent::class)
@Module
class WeatherRepositoryModule {

    @Provides
    fun provideWeatherRepository(defaultWeatherRepository: DefaultWeatherRepository): WeatherRepository =
        defaultWeatherRepository

}