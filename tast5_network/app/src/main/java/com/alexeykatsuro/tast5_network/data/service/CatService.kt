package com.alexeykatsuro.tast5_network.data.service

import com.alexeykatsuro.tast5_network.data.dto.CatDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatService {
    @GET("images/search")
    suspend fun fetchCats(
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null,
        @Query("order") order: String? = "asc"
    ): List<CatDto>
}