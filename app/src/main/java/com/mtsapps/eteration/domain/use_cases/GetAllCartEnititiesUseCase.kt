package com.mtsapps.eteration.domain.use_cases

import com.mtsapps.eteration.data.repository.CartRepositoryImpl
import javax.inject.Inject

class GetAllCartEnititiesUseCase @Inject constructor(private val cartRepositoryImpl: CartRepositoryImpl) {
    operator fun invoke()=cartRepositoryImpl.getAllCartEntities()
}