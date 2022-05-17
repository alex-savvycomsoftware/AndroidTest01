package com.savvycom.ui.posts

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.savvycom.core.base.BaseViewModel
import com.savvycom.core.extensions.textChanges
import com.savvycom.data.response.PostModel
import com.savvycom.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

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

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun initSearch(searchView: EditText, onResult: (List<PostModel>) -> Unit) {
        viewModelScope.launch {
            searchView.textChanges()
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query -> searchPost(query) }
                .flowOn(Dispatchers.Default)
                .collect { result ->
                    onResult.invoke(result)
                }
        }
    }

    @SuppressLint("CheckResult")
    private fun searchPost(query: String): Flow<List<PostModel>> {
        return flow {
            listPost.asFlow()
                .map { post -> post.filter { item -> query.isEmpty() || item.title?.contains(query) == true || item.body?.contains(query) == true } }
                .collect {
                    emit(it)
                }
        }
    }

}