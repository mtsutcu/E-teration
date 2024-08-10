package com.mtsapps.eteration.commons

import retrofit2.HttpException
import retrofit2.Response

interface ApiHandler {
    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResponse<T> {
        return try {
            val response = execute()

            if (response.isSuccessful) {
                NetworkResponse.Success(response.code(), response.body()!!)
            } else {
                NetworkResponse.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: HttpException) {
            NetworkResponse.Error(e.code(), e.message())
        }
    }
}