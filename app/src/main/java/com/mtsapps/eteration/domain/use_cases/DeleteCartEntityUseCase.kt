package com.mtsapps.eteration.domain.use_cases

import com.mtsapps.eteration.data.local.entity.CartEntity
import com.mtsapps.eteration.data.repository.CartRepositoryImpl
import javax.inject.Inject

class DeleteCartEntityUseCase @Inject constructor(private val cartRepositoryImpl: CartRepositoryImpl) {
    suspend  operator fun invoke(cartEntity: CartEntity)=cartRepositoryImpl.deleteCartEntity(cartEntity)
}