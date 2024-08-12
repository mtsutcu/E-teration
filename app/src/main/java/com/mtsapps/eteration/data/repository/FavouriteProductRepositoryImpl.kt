package com.mtsapps.eteration.data.repository

import com.mtsapps.eteration.data.local.FavoriteProductDao
import com.mtsapps.eteration.data.local.entity.FavoriteProduct
import com.mtsapps.eteration.domain.repository.FavouriteProductRepository
import kotlinx.coroutines.flow.Flow

class FavouriteProductRepositoryImpl(private val favoriteProductDao: FavoriteProductDao) : FavouriteProductRepository {
    override suspend fun insert(favoriteProduct: FavoriteProduct) = favoriteProductDao.insert(favoriteProduct)

    override suspend fun delete(productId: String) =favoriteProductDao.delete(productId)

    override fun getAllFavorites(): Flow<List<FavoriteProduct>> {
       return favoriteProductDao.getAllFavorites()
    }

    override fun isFavorite(productId: String): Flow<FavoriteProduct?> {
        return favoriteProductDao.isFavorite(productId)
    }
}