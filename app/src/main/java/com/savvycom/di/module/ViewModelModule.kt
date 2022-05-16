package com.savvycom.di.module

import com.savvycom.ui.auth.login.LoginViewModel
import com.savvycom.ui.auth.qrScan.QrScanViewModel
import com.savvycom.ui.auth.select_project.SelectProjectViewModel
import com.savvycom.MainViewModel
import com.savvycom.ui.home.capture.CaptureViewModel
import com.savvycom.ui.home.liveTracking.LiveTrackingViewModel
import com.savvycom.ui.home.help.slide.SlideViewModel
import com.savvycom.ui.home.setting.SettingViewModel
import com.savvycom.ui.home.start.StartViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}