package com.dohyun.searchimgapp.data.repository

import com.dohyun.searchimgapp.data.network.ApiService
import com.dohyun.searchimgapp.data.entity.ResponseData
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): SearchRepository {
    override suspend fun search(query: String, sort: String, page: Int): Response<ResponseData> {
        return apiService.search(query = query, sort = sort, page = page, size = 30)
    }

}