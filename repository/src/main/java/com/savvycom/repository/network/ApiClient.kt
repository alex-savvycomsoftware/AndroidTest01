package com.savvycom.repository.network

import com.savvycom.core.utils.ParseUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun getRetrofitBuilder(url: String, builder: OkHttpClient.Builder): Retrofit.Builder {
    builder.apply {
        connectTimeout(ApiConfiguration.TIME_OUT, TimeUnit.MINUTES)
        readTimeout(ApiConfiguration.TIME_OUT, TimeUnit.MINUTES)
        writeTimeout(ApiConfiguration.TIME_OUT, TimeUnit.MINUTES)
        callTimeout(ApiConfiguration.TIME_OUT, TimeUnit.MINUTES)

        val moshi = MoshiConverterFactory.create(ParseUtil.moshi).asLenient()
        return Retrofit.Builder().apply {
            baseUrl(url)
            addConverterFactory(moshi)
            client(builder.build())
        }
    }
}
