package com.ct.api

sealed class APIResponse<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T) : APIResponse<T>(data = data)
    class Failure<T>(error: String) : APIResponse<T>(error = error)
    class Loading<T>() : APIResponse<T>()
}
