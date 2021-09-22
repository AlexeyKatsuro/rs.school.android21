package com.alexeykatsuro.tast5_network.domain.repository

import com.alexeykatsuro.tast5_network.data.dto.CatDto
import com.alexeykatsuro.tast5_network.domain.service.CatService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatRepository(private val catService: CatService) {
    suspend fun fetchCats(limit: Int? = null, page: Int? = null): Result<List<CatDto>> =
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                catService.fetchCats(limit = limit, page = page)
            }
        }
}