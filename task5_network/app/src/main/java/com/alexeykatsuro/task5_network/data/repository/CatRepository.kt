package com.alexeykatsuro.task5_network.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alexeykatsuro.task5_network.data.dto.CatDto
import com.alexeykatsuro.task5_network.data.datasource.CatPagingSource
import com.alexeykatsuro.task5_network.data.service.CatService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CatRepository(private val catService: CatService) {
    suspend fun fetchCats(limit: Int? = null, page: Int? = null): Result<List<CatDto>> =
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                catService.fetchCats(limit = limit, page = page)
            }
        }

    fun pagesOfCats(initialPage: Int, pageSize: Int): Flow<PagingData<CatDto>> = Pager(
        initialKey = initialPage,
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CatPagingSource(catService) }
    ).flow
}

/*
*  val response = catService.fetchCats(limit = limit, page = page).awaitResponse()
                if (response.isSuccessful) {
                    val totalCount: Int
                    val currentPage: Int
                    val currentPageCount: Int
                    response.headers().apply {
                        totalCount = get("pagination-count")!!.toInt()
                        currentPage = get("pagination-page")!!.toInt()
                        currentPageCount = get("pagination-limit")!!.toInt()
                    }
                    response.body()!!
                } else {
                    throw HttpException(response)
                }*/