package com.mtsapps.eteration.data.repository

import com.mtsapps.eteration.data.local.CartDao
import com.mtsapps.eteration.data.local.entity.CartEntity
import com.mtsapps.eteration.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {
    override suspend fun insertCartEntity(cartEntity: CartEntity) {
        val existingCartEntity = cartDao.getCartItemByProductId(cartEntity.productId)
        if (existingCartEntity != null) {
            val newCount = existingCartEntity.count+1
            val updatedCartItem = existingCartEntity.copy(count = newCount)
            cartDao.updateCart(updatedCartItem)
        } else {
            cartDao.insertCart(cartEntity)
        }
    }

    override suspend fun deleteCartEntity(cartEntity: CartEntity) {
        cartDao.deleteCart(cartEntity)
    }

    override suspend fun updateCartEntity(cartEntity: CartEntity) {
        cartDao.updateCart(cartEntity)
    }

    override suspend fun completeCart() {
        cartDao.deleteAllEntities()
    }

    override fun getAllCartEntities(): Flow<List<CartEntity>> {
        return cartDao.getAllCartEntities()
    }
}