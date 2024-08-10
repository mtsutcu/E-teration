package com.mtsapps.eteration.presentation.detail

import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(

) : BaseViewModel<DetailUIEvent, DetailUIState, DetailUIEffect>() {
    override fun createInitialState(): DetailUIState {
        return DetailUIState()
    }

    override fun handleEvent(event: DetailUIEvent) {
        when (event) {
            is DetailUIEvent.OnDefaultEvent -> defaultFunc()
        }
    }

   private fun defaultFunc() {}
}

data class DetailUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
) :
    UIState

sealed class DetailUIEvent : UIEvent {
    data object OnDefaultEvent : DetailUIEvent()
}

sealed class DetailUIEffect : UIEffect {
    data class ShowToast(val message: String) : DetailUIEffect()
}