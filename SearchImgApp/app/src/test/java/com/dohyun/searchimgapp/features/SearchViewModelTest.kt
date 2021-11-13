package com.dohyun.searchimgapp.features

import com.dohyun.searchimgapp.data.SearchApiTest
import com.dohyun.searchimgapp.data.entity.ErrorResponse
import com.dohyun.searchimgapp.data.entity.ImageInfo
import com.dohyun.searchimgapp.data.entity.ResponseData
import com.dohyun.searchimgapp.data.network.NetworkResponse
import com.dohyun.searchimgapp.data.network.api.ApiService
import com.dohyun.searchimgapp.data.repository.SearchRepository
import com.dohyun.searchimgapp.data.repository.SearchRepositoryImpl
import com.dohyun.searchimgapp.utils.BaseUnitTest
import com.dohyun.searchimgapp.utils.getOrAwaitValueAndroidTest
import com.dohyun.searchimgapp.utils.getValueForTest
import com.dohyun.searchimgapp.view.search.SearchViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.mock
import java.lang.RuntimeException

@RunWith(JUnit4::class)
class SearchViewModelTest : BaseUnitTest() {

    private lateinit var viewModel: SearchViewModel

    @Mock
    lateinit var searchRepository: SearchRepository

    @MockK
    lateinit var mockApiService: ApiService

    private val mockOutput = mockk<NetworkResponse<ResponseData, ErrorResponse>>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        searchRepository = SearchRepositoryImpl(mockApiService)
        viewModel = SearchViewModel(searchRepository)
    }

    @Test
    fun `test getSearchList`() {
        val query = "jeju"
        val page = 1
        val sort = "accuracy"
        coEvery { searchRepository.search(query, sort, page) } returns mockOutput
        viewModel.searchImg(query)
        coVerify { searchRepository.search(query, sort, page) }
    }

    @Test
    fun `test nextSearch`() {
        val query = "jeju"
        val page = 1
        val sort = "accuracy"
        coEvery { searchRepository.search(query, sort, page+1) } returns mockOutput
        viewModel.next(query)
        coVerify { searchRepository.search(query, sort, page+1) }
    }
}