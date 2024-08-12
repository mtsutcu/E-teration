package com.mtsapps.eteration.domain.repository

import androidx.paging.Pager
import com.mtsapps.eteration.domain.models.Product

interface ProductRepository {
    suspend fun getProducts(filter : String,order : String,brand : String,model : String,sortedBy : String) : Pager<Int, Product>
}