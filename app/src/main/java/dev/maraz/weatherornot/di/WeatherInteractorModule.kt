package dev.maraz.weatherornot.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.maraz.weatherornot.domain.DefaultWeatherInteractor
import dev.maraz.weatherornot.domain.WeatherInteractor

@InstallIn(ApplicationComponent::class)
@Module
class WeatherInteractorModule {

    @Provides
    fun provideWeatherInteractor(defaultWeatherInteractor: DefaultWeatherInteractor): WeatherInteractor =
        defaultWeatherInteractor

}