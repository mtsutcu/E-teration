package com.mtsapps.eteration.domain.use_cases

import com.mtsapps.eteration.data.repository.CartRepositoryImpl
import javax.inject.Inject

class CompleteCartUseCase @Inject constructor(private val cartRepositoryImpl: CartRepositoryImpl) {
   suspend operator fun invoke()=cartRepositoryImpl.completeCart()
}