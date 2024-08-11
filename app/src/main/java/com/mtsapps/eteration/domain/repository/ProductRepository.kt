package com.mtsapps.eteration.domain.repository

import androidx.paging.Pager
import com.mtsapps.eteration.domain.models.Product

interface ProductRepository {
    suspend fun getProducts() : Pager<Int, Product>
}