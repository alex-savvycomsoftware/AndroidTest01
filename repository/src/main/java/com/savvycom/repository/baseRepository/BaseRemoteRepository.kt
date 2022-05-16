package com.savvycom.repository.baseRepository

import com.savvycom.repository.network.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface BaseRemoteRepository {

    @Suppress("UNCHECKED_CAST")
    suspend fun <G : Any, T : Any> makeApiCall(call: suspend () -> Response<G>): ApiResponse<T>

    fun <T : Any> handleError(errorCode: Int?, errorBody: ResponseBody?): ApiResponse<T>
}