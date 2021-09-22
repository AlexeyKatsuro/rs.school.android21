package com.alexeykatsuro.tast5_network

import android.app.Application
import com.alexeykatsuro.tast5_network.domain.repository.CatRepository
import com.alexeykatsuro.tast5_network.domain.service.CatService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.create

class App : Application() {

    companion object {
        private const val baseUrl = "https://api.thecatapi.com/v1/";
    }

    private val retrofit: Retrofit by lazy {

        val contentType = MediaType.get("application/json")
        val json = Json {
            ignoreUnknownKeys = true
        }
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
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