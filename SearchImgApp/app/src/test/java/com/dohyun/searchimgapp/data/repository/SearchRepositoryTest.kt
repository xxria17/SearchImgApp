package com.dohyun.searchimgapp.data.repository

import android.accounts.NetworkErrorException
import com.dohyun.searchimgapp.data.entity.ErrorResponse
import com.dohyun.searchimgapp.data.entity.ImageInfo
import com.dohyun.searchimgapp.data.entity.MetaData
import com.dohyun.searchimgapp.data.entity.ResponseData
import com.dohyun.searchimgapp.data.network.NetworkResponse
import com.dohyun.searchimgapp.data.network.api.ApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class SearchRepositoryTest {

    @MockK
    lateinit var mockApiService: ApiService

    private lateinit var searchRepository: SearchRepository

    @MockK
    lateinit var mockData : NetworkResponse<ResponseData, ErrorResponse>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        searchRepository = SearchRepositoryImpl(mockApiService)
    }

    val query = "seoul"
    val page = 1
    val sort = "accuracy"

    @Test
    fun `test getSearchList no network`() {
        coEvery { mockApiService.search(query = query, page = page, sort = sort, size = 30) } throws NetworkErrorException()

        val output = runBlocking { searchRepository.search(query = query, sort = sort, page = page) }

        Assert.assertTrue(output is NetworkResponse.NetworkError)
        Assert.assertEquals(NetworkResponse.NetworkError(IOException()), (output as NetworkResponse.NetworkError).error)

        coVerify { mockApiService.search(query = query, page = page, sort = sort, size = 30) }
    }

    @Test
    fun `test getSearchList with empty result`() {
        val wrongQuery = "qjfie"
        coEvery { mockApiService.search(query = wrongQuery, page = page, sort = sort, size = 30) } returns mockData

        val output = runBlocking { searchRepository.search(query = wrongQuery, sort = sort, page = page) }

        Assert.assertTrue(output is NetworkResponse.Success)
        Assert.assertEquals(0, (output as NetworkResponse.Success).body.metaData.totalCount)

        coVerify { mockApiService.search(query = wrongQuery, page = page, sort = sort, size = 30) }
    }

    @Test
    fun `test getSearchList with correct result`() {
        coEvery { mockApiService.search(query = query, page = page, sort = sort, size = 30) } returns mockData

        val output = runBlocking { searchRepository.search(query = query, sort = sort, page = page) }

        Assert.assertTrue(output is NetworkResponse.Success)
        Assert.assertEquals(3948, (output as NetworkResponse.Success).body.metaData.pageableCount)
        coVerify { mockApiService.search(query = query, page = page, sort = sort, size = 30) }
    }
}