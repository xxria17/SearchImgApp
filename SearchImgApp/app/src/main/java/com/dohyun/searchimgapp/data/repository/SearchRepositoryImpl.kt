package com.dohyun.searchimgapp.data.repository

import com.dohyun.searchimgapp.data.ApiService
import com.dohyun.searchimgapp.data.entity.ResponseData
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): SearchRepository {
    override suspend fun search(query: String, sort: String): Response<ResponseData> {
        return apiService.search(query = query, sort = sort, page = 1, size = 30)
    }

}