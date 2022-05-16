package com.savvycom.di.module

import com.savvycom.MainViewModel
import com.savvycom.ui.posts.PostViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        PostViewModel(get())
    }
}