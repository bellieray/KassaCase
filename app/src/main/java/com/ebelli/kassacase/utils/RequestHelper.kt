package com.ebelli.kassacase.utils

import retrofit2.Response

sealed class NetworkResult<T> {
    class Success<T>(val data: T? = null) : NetworkResult<T>()
    class Failed<T>(val exception: String?) : NetworkResult<T>()
}

suspend fun <T> apiCall(request: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = request()
        if (response.isSuccessful) {
            NetworkResult.Success(response.body())
        } else {
            NetworkResult.Failed(response.message())
        }
    } catch (e: Exception) {
        NetworkResult.Failed(e.localizedMessage)
    }
}