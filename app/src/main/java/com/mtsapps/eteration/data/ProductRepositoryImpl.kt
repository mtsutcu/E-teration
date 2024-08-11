package com.mtsapps.eteration.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mtsapps.eteration.domain.models.Product
import com.mtsapps.eteration.domain.repository.ProductRepository

class ProductRepositoryImpl(private val apiService: ApiService) : ProductRepository {
    override suspend fun getProducts(filter : String): Pager<Int, Product> {
            return Pager(
                config = PagingConfig(
                    pageSize = 4,
                    initialLoadSize = 4,
                    enablePlaceholders = true,
                    prefetchDistance = 1
                ),
                pagingSourceFactory = { ProductPagingDataSource(apiService = apiService, filter = filter) }
            )
        }




}