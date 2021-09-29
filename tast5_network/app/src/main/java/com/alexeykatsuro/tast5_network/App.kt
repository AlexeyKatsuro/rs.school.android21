package com.alexeykatsuro.tast5_network

import android.app.Application
import com.alexeykatsuro.tast5_network.domain.repository.CatRepository
import com.alexeykatsuro.tast5_network.domain.service.CatService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create


class App : Application() {

    companion object {
        private const val baseUrl = "https://api.thecatapi.com/v1/"
    }

    private val httpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @ExperimentalSerializationApi
    private val retrofit: Retrofit by lazy {

        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(httpClient)
            .build()
    }

    private val catService: CatService by lazy {
        retrofit.create()
    }
    private val catRepository: CatRepository by lazy {
        CatRepository(catService = catService)
    }
    val viewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory(catRepository = catRepository)
    }
}