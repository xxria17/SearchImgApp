package com.dohyun.searchimgapp.data.network

import com.dohyun.searchimgapp.data.Constant
import com.dohyun.searchimgapp.data.entity.ResponseData
import retrofit2.Response
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
    ): Response<ResponseData>
}