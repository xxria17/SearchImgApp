package com.dohyun.searchimgapp.data

import com.dohyun.searchimgapp.data.network.NetworkResponse
import com.dohyun.searchimgapp.utils.MainCoroutineRule
import com.dohyun.searchimgapp.data.network.NetworkResponseAdapterFactory
import com.dohyun.searchimgapp.data.network.api.ApiService
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class SearchApiTest {

    @ExperimentalCoroutinesApi
    @get: Rule
    private val scheduleRule = MainCoroutineRule()

    lateinit var apiService: ApiService

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        apiService = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
            .create(ApiService::class.java)

    }

    @After
    fun teardown() {
    }

    @Test
    fun `checkApiTest`() {
        val query = "seoul"
        val page = 1
        val sort = "accuracy"
        val output = runBlocking { apiService.search(query = query, sort = sort, page = page, size = 30) }
        Assert.assertEquals(3948, (output as NetworkResponse.Success).body.metaData.pageableCount)

    }
}