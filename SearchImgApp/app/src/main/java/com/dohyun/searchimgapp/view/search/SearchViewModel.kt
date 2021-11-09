package com.dohyun.searchimgapp.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dohyun.searchimgapp.data.entity.ResponseData
import com.dohyun.searchimgapp.data.network.NetworkResponse
import com.dohyun.searchimgapp.data.repository.SearchRepository
import com.dohyun.searchimgapp.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): BaseViewModel() {

    private val _entireProgressVisible = MutableLiveData<Boolean>()
    val entireProgressVisible : LiveData<Boolean>
        get() = _entireProgressVisible

    private val _bottomProgressVisible = MutableLiveData<Boolean>()
    val bottomProgressVisible : LiveData<Boolean>
        get() = _bottomProgressVisible

    private val _result = MutableLiveData<ResponseData>()
    val result : LiveData<ResponseData>
        get() = _result

    private var isEnd : Boolean = false

    private val _msg = MutableLiveData<String>()
    val errorMsg : LiveData<String>
        get() = _msg

    private var count = 1

    fun searchImg(query: String) {
        count = 1
        _entireProgressVisible.postValue(true)
        viewModelScope.launch {
            val response = repository.search(query = query, sort = "accuracy", count)
            when(response) {
                is NetworkResponse.Success -> {
                    _result.postValue(response.body)
                }
                is NetworkResponse.ApiError -> {
                    _msg.postValue(errorHandle(response.body.errorType))
                }
                is NetworkResponse.NetworkError -> {
                    _msg.postValue(errorHandle("NetworkError"))
                }
                is NetworkResponse.UnknownError -> {
                    _msg.postValue(errorHandle("UnknownError"))
                }
            }

            _entireProgressVisible.postValue(false)
            isEnd = false
        }
    }

    fun next(query: String) {
        if (!isEnd) {
            _bottomProgressVisible.postValue(true)
            val currentList = _result.value
            viewModelScope.launch {
                count++
                val response = repository.search(query = query, sort = "accuracy", count)
                when(response) {
                    is NetworkResponse.Success -> {
                        isEnd = response.body.metaData.isEnd

                        val current = currentList!!.documents
                        val merged = current.toMutableList()
                                .apply { addAll(response.body.documents) }
                        val refresh = ResponseData(response.body.metaData, merged)
                        _result.postValue(refresh)
                    }
                    is NetworkResponse.ApiError -> {
                        _msg.postValue(errorHandle(response.body.errorType))
                    }
                    is NetworkResponse.NetworkError -> {
                        _msg.postValue(errorHandle("NetworkError"))
                    }
                    is NetworkResponse.UnknownError -> {
                        _msg.postValue(errorHandle("UnknownError"))
                    }
                }
                _bottomProgressVisible.postValue(false)
            }
        }
    }

    private fun errorHandle(msg: String): String {
        when (msg) {
            "MissingParameter" -> return "검색어를 입력해주세요."
            "UnknownError" -> return "잘못된 검색어입니다."
            "NetworkError" -> return "네트워크를 확인해주세요."
            else -> return "다시 시도해주세요."
        }
    }

}