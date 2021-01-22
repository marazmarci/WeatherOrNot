package dev.maraz.weatherornot.data.source.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitFactory {

    inline fun <reified T : Any> createRetrofitApi(apiRoot: String): T =
        createRetrofit(apiRoot).create(T::class.java)

    private fun createOkHttpClientWithInterceptors() =
        OkHttpClient().newBuilder().apply {
            interceptors().add(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }.build()

    fun createRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                @Suppress("EXPERIMENTAL_API_USAGE")
                Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory("application/json".toMediaType())
            )
            .client(createOkHttpClientWithInterceptors())
            .build()

}