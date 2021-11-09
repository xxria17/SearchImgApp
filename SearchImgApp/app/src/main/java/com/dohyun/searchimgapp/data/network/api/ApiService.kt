package com.dohyun.searchimgapp.data.network.api

import com.dohyun.searchimgapp.data.Constant
import com.dohyun.searchimgapp.data.entity.ErrorResponse
import com.dohyun.searchimgapp.data.entity.ResponseData
import com.dohyun.searchimgapp.data.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("v2/search/image")
    suspend fun search(
            @Header("Authorization") apiKey: String = Constant.AUTH_KEY,
            @Query("query") query: String,
            @Query("sort") sort: String,
            @Query("page") page: Int,
            @Query("size") size: Int
    ): NetworkResponse<ResponseData, ErrorResponse>
}