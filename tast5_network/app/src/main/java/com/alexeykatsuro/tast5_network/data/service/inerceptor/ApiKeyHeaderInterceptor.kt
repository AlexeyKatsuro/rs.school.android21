package com.alexeykatsuro.tast5_network.data.service.inerceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyHeaderInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("x-api-key", apiKey)
                .build()
        )
    }
}