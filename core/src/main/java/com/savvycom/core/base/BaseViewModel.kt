package com.savvycom.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.savvycom.core.utils.liveData.Event

open class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData(false)
    val isEmpty = MutableLiveData(false)
    val errorCode = MutableLiveData<Int>()
}