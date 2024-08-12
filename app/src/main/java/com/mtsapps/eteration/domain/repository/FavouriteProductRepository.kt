package com.mtsapps.eteration.domain.repository

import com.mtsapps.eteration.data.local.entity.FavoriteProduct
import kotlinx.coroutines.flow.Flow

interface FavouriteProductRepository {
    suspend fun insert(favoriteProduct: FavoriteProduct)
    suspend fun delete(productId: String)
    fun getAllFavorites(): Flow<List<FavoriteProduct>>
    fun isFavorite(productId: String): Flow<FavoriteProduct?>
}