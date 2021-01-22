package dev.maraz.weatherornot.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.maraz.weatherornot.data.source.local.AppDatabase
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object WeatherDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.createInstance(appContext)

    @Provides
    fun provideWeatherDao(database: AppDatabase) = database.weatherDao()

}