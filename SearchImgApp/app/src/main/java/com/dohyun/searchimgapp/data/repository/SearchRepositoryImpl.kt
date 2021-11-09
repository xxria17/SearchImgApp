package com.dohyun.searchimgapp.data.repository

import com.dohyun.searchimgapp.data.entity.ErrorResponse
import com.dohyun.searchimgapp.data.network.api.ApiService
import com.dohyun.searchimgapp.data.entity.ResponseData
import com.dohyun.searchimgapp.data.network.NetworkResponse
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): SearchRepository {
    override suspend fun search(query: String, sort: String, page: Int): NetworkResponse<ResponseData, ErrorResponse> {
        return apiService.search(query = query, sort = sort, page = page, size = 30)
    }

}