package com.savvycom.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.savvycom.BuildConfig
import com.savvycom.repository.component.AppDatabase
import com.savvycom.repository.component.AppPreferences
import com.savvycom.repository.component.DatabaseConfiguration
import com.savvycom.repository.network.getRetrofitBuilder
import com.savvycom.repository.network.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


val appModule = module {
    single {
        provideApiService(get())
    }
    single {
        provideOkHttpClient()
    }

    single {
        provideRetrofit(get(), BuildConfig.BASE_URL)
    }

    single {
        providePreferences(androidApplication())
    }

    single {
        provideDatabase(androidApplication())
    }
}

private fun provideOkHttpClient(): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor { message ->
            if (!message.contains("�")) {
                Timber.d(message);
            }
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(logging)
    }
    return builder
}

private fun provideRetrofit(okHttpClient: OkHttpClient.Builder, BASE_URL: String): Retrofit =
    getRetrofitBuilder(BASE_URL, okHttpClient).build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

private fun providePreferences(app: Application): AppPreferences = AppPreferences(app)

private fun provideDatabase(app: Application): AppDatabase {
    return Room.databaseBuilder(
        app.applicationContext,
        AppDatabase::class.java,
        DatabaseConfiguration.name
    ).build()
}
