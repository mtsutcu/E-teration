package com.mtsapps.eteration.presentation.favourites

import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(

) : BaseViewModel<FavouritesUIEvent, FavouritesUIState, FavouritesUIEffect>() {
    override fun createInitialState(): FavouritesUIState {
        return FavouritesUIState()
    }

    override fun handleEvent(event: FavouritesUIEvent) {
        when (event) {
            is FavouritesUIEvent.OnDefaultEvent -> defaultFunc()
        }
    }

   private fun defaultFunc() {}
}

data class FavouritesUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
) :
    UIState

sealed class FavouritesUIEvent : UIEvent {
    data object OnDefaultEvent : FavouritesUIEvent()
}

sealed class FavouritesUIEffect : UIEffect {
    data class ShowToast(val message: String) : FavouritesUIEffect()
}