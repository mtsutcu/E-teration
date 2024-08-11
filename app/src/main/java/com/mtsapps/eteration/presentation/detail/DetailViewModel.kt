package com.mtsapps.eteration.presentation.detail

import androidx.lifecycle.viewModelScope
import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import com.mtsapps.eteration.domain.models.Product
import com.mtsapps.eteration.domain.use_cases.InsertCartEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
private val insertCartEntityUseCase: InsertCartEntityUseCase
) : BaseViewModel<DetailUIEvent, DetailUIState, DetailUIEffect>() {
    override fun createInitialState(): DetailUIState {
        return DetailUIState()
    }

    override fun handleEvent(event: DetailUIEvent) {
        when (event) {
            is DetailUIEvent.OnAddCartEntity -> addCartEntity(event.product)
        }
    }

   private fun addCartEntity(product: Product) {
       viewModelScope.launch {
           insertCartEntityUseCase(product)
       }
   }
}

data class DetailUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
) :
    UIState

sealed class DetailUIEvent : UIEvent {
    data class OnAddCartEntity(val product: Product) : DetailUIEvent()
}

sealed class DetailUIEffect : UIEffect {
    data class ShowToast(val message: String) : DetailUIEffect()
}