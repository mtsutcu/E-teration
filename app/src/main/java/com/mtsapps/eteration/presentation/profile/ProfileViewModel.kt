package com.mtsapps.eteration.presentation.profile

import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(

) : BaseViewModel<ProfileUIEvent, ProfileUIState, ProfileUIEffect>() {
    override fun createInitialState(): ProfileUIState {
        return ProfileUIState()
    }

    override fun handleEvent(event: ProfileUIEvent) {
        when (event) {
            is ProfileUIEvent.OnDefaultEvent -> defaultFunc()
        }
    }

   private fun defaultFunc() {}
}

data class ProfileUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
) : UIState

sealed class ProfileUIEvent : UIEvent {
    data object OnDefaultEvent : ProfileUIEvent()
}

sealed class ProfileUIEffect : UIEffect {
    data class ShowToast(val message: String) : ProfileUIEffect()
}