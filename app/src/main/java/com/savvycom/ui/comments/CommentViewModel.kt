package com.savvycom.ui.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.savvycom.core.base.BaseViewModel
import com.savvycom.data.response.CommentModel
import com.savvycom.data.response.PostModel
import com.savvycom.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentViewModel(private val repository: Repository) : BaseViewModel() {

    val isRefreshing = MutableLiveData(false)

    val postData = MutableLiveData<PostModel>()

    val listComment = MutableLiveData<List<CommentModel>>()

    fun getListComment(refresh: Boolean = false) {
        postData.value?.id?.let {
            isLoading.postValue(!refresh)
            isRefreshing.postValue(refresh)
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    repository.getPostComments(it)
                }
                if (response.isSuccess) {
                    listComment.postValue(response.data ?: ArrayList())
                    isEmpty.postValue(response.data.isNullOrEmpty())
                } else {
                    errorCode.postValue(response.code ?: 0)
                }
                isLoading.postValue(false)
                isRefreshing.postValue(false)
            }
        }
    }


}