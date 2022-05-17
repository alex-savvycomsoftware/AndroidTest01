package com.savvycom.ui.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.savvycom.core.base.BaseViewModel
import com.savvycom.data.response.PostModel
import com.savvycom.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostViewModel(private val repository: Repository) : BaseViewModel() {

    val isRefreshing = MutableLiveData(false)
    val listPost = MutableLiveData<List<PostModel>>()

    fun getListPost(refresh: Boolean = false) {
        isLoading.postValue(!refresh)
        isRefreshing.postValue(refresh)
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getListPost()
            }
            if (response.isSuccess) {
                listPost.postValue(response.data ?: ArrayList())
                isEmpty.postValue(response.data.isNullOrEmpty())
                if (!response.data.isNullOrEmpty()) {
                    repository.insertListPost(response.data!!)
                }
            } else {
                errorCode.postValue(response.code ?: 0)
            }
            isLoading.postValue(false)
            isRefreshing.postValue(false)
        }
    }
}