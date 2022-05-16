package com.savvycom.repository.baseRepository

import com.savvycom.core.type.RequestStatus
import com.savvycom.core.utils.ParseUtil
import com.savvycom.repository.R
import com.savvycom.repository.network.ApiResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Types
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.asResponseBody
import org.json.JSONException
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

class BaseRemoteRepositoryImp : BaseRemoteRepository {
    @Suppress("UNCHECKED_CAST")
    override suspend fun <G : Any, T : Any> makeApiCall(call: suspend () -> Response<G>): ApiResponse<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                ApiResponse(
                    isSuccess = true,
                    data = response.body() as T,
                    code = response.code()
                )
            } else {
                ApiResponse.ERROR(
                    errorCode = response.code(),
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            when (e) {
                is CancellationException -> ApiResponse.ERROR(
                    errorCode = RequestStatus.CANCEL_REQUEST.value
                )
                else -> ApiResponse.ERROR(
                    errorCode = RequestStatus.INTERNAL_SERVER_ERROR.value,
                )
            }
        }
    }

    override fun <T : Any> handleError(errorCode: Int?, errorBody: ResponseBody?): ApiResponse<T> {
        var errorResponse: ApiResponse<Any>? = null

        errorBody?.let {
            try {
                val json = it.string()
                if (json.isNotEmpty()) {
                    val payloadType =
                        Types.newParameterizedType(ApiResponse::class.java, Any::class.java)
                    val jsonAdapter: JsonAdapter<ApiResponse<Any>> =
                        ParseUtil.moshi.adapter(payloadType)
                    errorResponse = jsonAdapter.lenient().nullSafe().fromJson(json)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return ApiResponse.ERROR(
            message = "",
            errorCode = errorCode
        )

    }
}