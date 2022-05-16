package com.savvycom.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.savvycom.core.utils.liveData.Event

open class BaseViewModel : ViewModel() {
    enum class STATUS_SCREEN {
        LOADING,
        ERROR,
        DONE
    }

    val statusLiveData = MutableLiveData<Event<STATUS_SCREEN>>()
}