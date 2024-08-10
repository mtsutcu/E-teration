package com.mtsapps.eteration.commons

sealed class NetworkResponse<out T : Any> {
    data class Success<out T : Any>(val code: Int, val data: T) : NetworkResponse<T>()
    data class Error(val code: Int, val errorMsg: String?) : NetworkResponse<Nothing>()
}

inline fun <T : Any, R : Any> NetworkResponse<T>.map(transform: (T) -> R): NetworkResponse<R> {
    return when (this) {
        is NetworkResponse.Success -> NetworkResponse.Success(this.code, transform(this.data))
        is NetworkResponse.Error -> NetworkResponse.Error(this.code, this.errorMsg)
    }
}