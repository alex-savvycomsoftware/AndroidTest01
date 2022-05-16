package com.savvycom.repository.network

data class ApiResponse<T>(
    val data: T?,
    val code: Int? = null,
    var isSuccess: Boolean = true,
    val message: String? = null,
    val errorCode: Int? = null,
    val result: Boolean = false,
) {
    companion object {
        fun <T> ERROR(
            errorCode: Int? = null,
            message: String? = null
        ) =
            ApiResponse<T>(
                data = null,
                isSuccess = false,
                message = message,
                result = false,
                errorCode = errorCode
            )
    }

    fun <G> clone(dataG: G?) = ApiResponse<G>(
        data = dataG,
        isSuccess = isSuccess,
        errorCode = errorCode,
        message = message,
        code = code,
        result = result
    )
}