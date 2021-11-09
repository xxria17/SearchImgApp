package com.dohyun.searchimgapp.data.repository

import com.dohyun.searchimgapp.data.entity.ErrorResponse
import com.dohyun.searchimgapp.data.entity.ResponseData
import com.dohyun.searchimgapp.data.network.NetworkResponse

interface SearchRepository {
    suspend fun search(query: String, sort: String, page: Int): NetworkResponse<ResponseData, ErrorResponse>
}