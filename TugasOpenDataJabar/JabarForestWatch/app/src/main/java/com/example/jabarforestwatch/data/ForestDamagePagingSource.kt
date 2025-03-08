package com.example.jabarforestwatch.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class ForestDamagePagingSource(
    private val apiService: OpenDataJabarApiService
) : PagingSource<Int, ForestDamageModels>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ForestDamageModels> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getForestDamageData(page)
            Log.d("Open Data Jabar", "API Response on page $page: $response")
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("Open Data Jabar", "Error loading page $page", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ForestDamageModels>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}