package com.mtsapps.eteration.domain.repository

import com.mtsapps.eteration.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun insertCartEntity(cartEntity: CartEntity)
    suspend fun deleteCartEntity(cartEntity: CartEntity)
    suspend fun updateCartEntity(cartEntity: CartEntity)
    suspend fun completeCart()
    fun getAllCartEntities() : Flow<List<CartEntity>>
}