package com.dohyun.searchimgapp.data.repository

import com.dohyun.searchimgapp.data.entity.ResponseData
import retrofit2.Response

interface SearchRepository {
    suspend fun search(query: String, sort: String): Response<ResponseData>
}