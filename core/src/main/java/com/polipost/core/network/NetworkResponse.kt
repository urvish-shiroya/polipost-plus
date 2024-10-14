package com.polipost.core.network

sealed class NetworkResponse<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Default<T> : NetworkResponse<T>()
    class Loading<T> : NetworkResponse<T>()
    class Success<T>(data: T) : NetworkResponse<T>(data = data)
    class Failure<T>(error: Throwable) : NetworkResponse<T>(error = error)
}