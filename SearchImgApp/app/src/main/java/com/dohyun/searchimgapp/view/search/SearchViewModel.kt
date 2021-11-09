package com.dohyun.searchimgapp.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dohyun.searchimgapp.data.entity.ResponseData
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

    private val _isEnd = MutableLiveData<Boolean>(false)

    private var count = 1

    fun searchImg(query: String) {
        count = 1
        _entireProgressVisible.postValue(true)
        viewModelScope.launch {
            val response = repository.search(query = query, sort = "accuracy", count)
            _result.postValue(response.body())
            _entireProgressVisible.postValue(false)
            _isEnd.postValue(false)
        }
    }

    fun next(query: String) {
        if (!_isEnd.value!!) {
            _bottomProgressVisible.postValue(true)
            val currentList = _result.value
            viewModelScope.launch {
                count++
                val response = repository.search(query = query, sort = "accuracy", count)
                _isEnd.postValue(response.body()!!.metaData.isEnd)
                val current = currentList!!.documents
                val merged = current.toMutableList()
                        .apply { addAll(response.body()!!.documents) }
                val refresh = ResponseData(response.body()!!.metaData, merged)
                _result.postValue(refresh)
                _bottomProgressVisible.postValue(false)
            }
        }
    }
}