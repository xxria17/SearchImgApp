package com.dohyun.searchimgapp.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dohyun.searchimgapp.data.entity.ResponseData
import com.dohyun.searchimgapp.data.repository.SearchRepository
import com.dohyun.searchimgapp.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): BaseViewModel() {

    private val _progressVisible = MutableLiveData<Boolean>()
    val progressVisible : LiveData<Boolean>
        get() = _progressVisible

    private val _result = MutableLiveData<Response<ResponseData>>()
    val result : LiveData<Response<ResponseData>>
        get() = _result


    fun searchImg(query: String) {
        viewModelScope.launch {
            val response = repository.search(query= query, sort = "accuracy")
            println("SearchViewModel response $response")
            _result.postValue(response)
        }
    }
}