package com.mtsapps.eteration.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import com.mtsapps.eteration.data.ProductRepositoryImpl
import com.mtsapps.eteration.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepositoryImpl: ProductRepositoryImpl,
) : BaseViewModel<HomeUIEvent, HomeUIState, HomeUIEffect>() {
    override fun createInitialState(): HomeUIState {
        return HomeUIState()
    }

    override fun loadInitialData() {
        getProducts()
    }

    override fun handleEvent(event: HomeUIEvent) {
        when (event) {
            is HomeUIEvent.OnSetLoadingError -> setError(event.e)
            is HomeUIEvent.OnOpeningError -> setOpeningError()
            HomeUIEvent.OnTryAgain -> loadInitialData()
        }
    }

    private fun setError(e: Throwable) {
        setEffect { HomeUIEffect.ShowToast(e.cause?.message.toString()) }
    }
    private fun setOpeningError() {
        setState { copy(isError = true) }
    }
    private fun getProducts(){
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            delay(1000)
            productRepositoryImpl.getProducts().flow.cancellable().cachedIn(viewModelScope).collect{
                setState { copy(productList = it, isLoading = false, isError = false) }
            }
        }
    }
}

data class HomeUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val productList: PagingData<Product> = PagingData.empty()
) :
    UIState

sealed class HomeUIEvent : UIEvent {
    data object OnOpeningError : HomeUIEvent()
    data class OnSetLoadingError(val e: Throwable) : HomeUIEvent()
    data object OnTryAgain : HomeUIEvent()

}

sealed class HomeUIEffect : UIEffect {
    data class ShowToast(val message: String) : HomeUIEffect()
}
