package com.mtsapps.eteration.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mtsapps.eteration.data.remote.ApiService
import com.mtsapps.eteration.data.remote.ProductPagingDataSource
import com.mtsapps.eteration.domain.models.Product
import com.mtsapps.eteration.domain.repository.ProductRepository

class ProductRepositoryImpl(private val apiService: ApiService) : ProductRepository {
    override suspend fun getProducts(filter : String,order : String,brand : String,model : String,sortedBy : String): Pager<Int, Product> {
            return Pager(
                config = PagingConfig(
                    pageSize = 4,
                    initialLoadSize = 4,
                    enablePlaceholders = true,
                    prefetchDistance = 1
                ),
                pagingSourceFactory = { ProductPagingDataSource(apiService = apiService, filter = filter, order = order, brand = brand,model=model,sortedBy=sortedBy) }
            )
        }
}