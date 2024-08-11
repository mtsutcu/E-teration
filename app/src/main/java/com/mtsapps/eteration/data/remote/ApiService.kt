package com.mtsapps.eteration.data.remote

import com.mtsapps.eteration.domain.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(
        @Query("page") page: Int,
        @Query("limit") limit : Int,
        @Query("filter") filter : String,
    ): Response<List<Product>>
}