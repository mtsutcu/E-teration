package com.mtsapps.eteration.presentation.cart

import androidx.lifecycle.viewModelScope
import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import com.mtsapps.eteration.data.local.entity.CartEntity
import com.mtsapps.eteration.domain.use_cases.CompleteCartUseCase
import com.mtsapps.eteration.domain.use_cases.DeleteCartEntityUseCase
import com.mtsapps.eteration.domain.use_cases.GetAllCartEnititiesUseCase
import com.mtsapps.eteration.domain.use_cases.UpdateCartEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCartEntitiesUseCase: GetAllCartEnititiesUseCase,
    private val updateCartEntityUseCase: UpdateCartEntityUseCase,
    private val deleteCartEntityUseCase: DeleteCartEntityUseCase,
    private val completeCartUseCase: CompleteCartUseCase
) : BaseViewModel<CartUIEvent, CartUIState, CartUIEffect>() {
    override fun createInitialState(): CartUIState {
        return CartUIState()
    }

    override fun handleEvent(event: CartUIEvent) {
        when (event) {
            is CartUIEvent.OnGetAllCarts -> getAllCarts()
            is CartUIEvent.OnAddCartEntity -> addCart(event.cartEntity)
            is CartUIEvent.OnCompleteCart -> completeCart()
            is CartUIEvent.OnMinusCartCount -> minusCartEntityCount(event.cartEntity)
            is CartUIEvent.OnPlusCartCount -> plusCartEntityCount(event.cartEntity)
        }
    }


    private fun getAllCarts() {
        viewModelScope.launch {
            getAllCartEntitiesUseCase().collect {
                val totalPrice = it.sumOf { cartEntity -> cartEntity.totalPrice }
                setState { copy(cartList = it, totalPrice = totalPrice.toInt()) }
            }

        }
    }

    private fun addCart(cartEntity: CartEntity) {
        viewModelScope.launch {
            updateCartEntityUseCase(cartEntity)
        }
    }

    private fun completeCart() {
        viewModelScope.launch {
            completeCartUseCase()
            setEffect { CartUIEffect.ShowSnackBar }
        }
    }

    private fun minusCartEntityCount(cartEntity: CartEntity) {
        viewModelScope.launch {
            if (cartEntity.count > 1) {
                updateCartEntityUseCase(cartEntity.copy(count = cartEntity.count - 1))
            } else {
                deleteCartEntityUseCase(cartEntity)
            }
        }
    }

    private fun plusCartEntityCount(cartEntity: CartEntity) {
        viewModelScope.launch {
            updateCartEntityUseCase(cartEntity.copy(count = cartEntity.count + 1))
        }
    }
}

data class CartUIState(
    val cartList: List<CartEntity>? = null,
    val totalPrice: Int = 0
) : UIState

sealed class CartUIEvent : UIEvent {
    data object OnGetAllCarts : CartUIEvent()
    data class OnAddCartEntity(val cartEntity: CartEntity) : CartUIEvent()
    data object OnCompleteCart : CartUIEvent()
    data class OnMinusCartCount(val cartEntity: CartEntity) : CartUIEvent()
    data class OnPlusCartCount(val cartEntity: CartEntity) : CartUIEvent()
}

sealed class CartUIEffect : UIEffect {
    data object ShowSnackBar : CartUIEffect()
}