package com.mtsapps.eteration.presentation.card

import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(

) : BaseViewModel<CartUIEvent, CartUIState, CartUIEffect>() {
    override fun createInitialState(): CartUIState {
        return CartUIState()
    }

    override fun handleEvent(event: CartUIEvent) {
        when (event) {
            is CartUIEvent.OnDefaultEvent -> defaultFunc()
        }
    }

  private fun defaultFunc() {}
}

data class CartUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
) : UIState

sealed class CartUIEvent : UIEvent {
    data object OnDefaultEvent : CartUIEvent()
}

sealed class CartUIEffect : UIEffect {
    data class ShowToast(val message: String) : CartUIEffect()
}