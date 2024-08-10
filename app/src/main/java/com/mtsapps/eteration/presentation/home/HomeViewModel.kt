package com.mtsapps.eteration.presentation.home

import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(

) : BaseViewModel<HomeUIEvent, HomeUIState, HomeUIEffect>() {
    override fun createInitialState(): HomeUIState {
        return HomeUIState()
    }


    override fun handleEvent(event: HomeUIEvent) {
        when (event) {
            is HomeUIEvent.OnDefaultEvent -> defaultFunc()
        }
    }

   private fun defaultFunc() {}
}

data class HomeUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
) :
    UIState

sealed class HomeUIEvent : UIEvent {
    data object OnDefaultEvent : HomeUIEvent()
}

sealed class HomeUIEffect : UIEffect {
    data class ShowToast(val message: String) : HomeUIEffect()
}