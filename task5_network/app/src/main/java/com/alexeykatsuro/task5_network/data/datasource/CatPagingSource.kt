package com.alexeykatsuro.task5_network.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.alexeykatsuro.task5_network.data.dto.CatDto
import com.alexeykatsuro.task5_network.data.service.CatService
import retrofit2.HttpException
import java.io.IOException


class CatPagingSource(
    private val service: CatService,
) : PagingSource<Int, CatDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatDto> {
        fun nextPage(current: Int) = (current + 1)
        fun prevPage(current: Int) = (current - 1).takeIf { it >= 0 }
        return try {
            val key = params.key ?: 0
            val items = service.fetchCats(page = key, limit = params.loadSize)

            Page(
                data = items,
                prevKey = key.let(::prevPage),
                nextKey = key.let(::nextPage)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.let { it + 1 }
        }
    }
}
