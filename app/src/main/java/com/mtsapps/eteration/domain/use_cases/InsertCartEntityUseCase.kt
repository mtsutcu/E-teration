package com.mtsapps.eteration.domain.use_cases

import com.mtsapps.eteration.data.local.entity.CartEntity
import com.mtsapps.eteration.data.repository.CartRepositoryImpl
import com.mtsapps.eteration.domain.models.Product
import javax.inject.Inject

class InsertCartEntityUseCase @Inject constructor(private val cartRepositoryImpl: CartRepositoryImpl) {
   suspend  operator fun invoke(product: Product){
      product.id?.let {
         val cartEntity = CartEntity(
            productId = it.toInt(),
            name = product.name.toString(),
            price = product.price.toString(),
            count = 1
         )
         cartRepositoryImpl.insertCartEntity(cartEntity)
      }
   }
}