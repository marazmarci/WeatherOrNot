package dev.maraz.weatherornot.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.maraz.weatherornot.data.source.remote.MetaWeatherWebApi
import dev.maraz.weatherornot.data.source.remote.RetrofitFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object MetaWeatherWebApiModule {

    private const val baseUrl = "https://www.metaweather.com/api/"

    @Provides
    @Singleton
    fun provideMetaWeatherWebApi() = RetrofitFactory.createRetrofitApi<MetaWeatherWebApi>(baseUrl)

}