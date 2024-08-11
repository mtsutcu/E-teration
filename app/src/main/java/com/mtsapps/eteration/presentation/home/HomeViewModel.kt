package com.mtsapps.eteration.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import com.mtsapps.eteration.data.repository.ProductRepositoryImpl
import com.mtsapps.eteration.domain.models.Product
import com.mtsapps.eteration.domain.use_cases.InsertCartEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepositoryImpl: ProductRepositoryImpl,
    private val insertCartEntityUseCase: InsertCartEntityUseCase
) : BaseViewModel<HomeUIEvent, HomeUIState, HomeUIEffect>() {
    override fun createInitialState(): HomeUIState {
        return HomeUIState()
    }

    override fun loadInitialData() {
        viewModelScope.launch {
            delay(1000)
            getProducts()
        }
    }

    override fun handleEvent(event: HomeUIEvent) {
        when (event) {
            is HomeUIEvent.OnSetLoadingError -> setError(event.e)
            is HomeUIEvent.OnOpeningError -> setOpeningError()
            is HomeUIEvent.OnTryAgain -> tryAgain()
            is HomeUIEvent.OnSetFilterText -> setFilterText(event.text)
            is HomeUIEvent.OnAddCartEntity -> addCartEntity(event.product)
        }
    }

    private fun setError(e: Throwable) {
        setEffect { HomeUIEffect.ShowToast(e.cause?.message.toString()) }
    }

    private fun setOpeningError() {
        setState { copy(isError = true) }
    }

    private fun getProducts() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            productRepositoryImpl.getProducts(filter = uiState.value.filterText).flow.cancellable()
                .cachedIn(viewModelScope).collect {
                setState { copy(productList = it, isLoading = false, isError = false) }
            }
        }
    }

    private fun tryAgain() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            delay(1000)
            productRepositoryImpl.getProducts(filter = uiState.value.filterText).flow.cancellable()
                .cachedIn(viewModelScope).collect {
                setState { copy(productList = it, isLoading = false, isError = false) }
            }
        }
    }

    private fun setFilterText(text: String) {
        setState { copy(filterText = text) }
        getProducts()
    }

    private fun addCartEntity(product: Product) {
        viewModelScope.launch {
            insertCartEntityUseCase(product)
        }
        setEffect { HomeUIEffect.ShowSnackBar(message = "${product.name} Added") }
    }
}

data class HomeUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val productList: PagingData<Product> = PagingData.empty(),
    val filterText: String = ""
) :
    UIState

sealed class HomeUIEvent : UIEvent {
    data object OnOpeningError : HomeUIEvent()
    data class OnSetLoadingError(val e: Throwable) : HomeUIEvent()
    data object OnTryAgain : HomeUIEvent()
    data class OnSetFilterText(val text: String) : HomeUIEvent()
    data class OnAddCartEntity(val product: Product) : HomeUIEvent()


}

sealed class HomeUIEffect : UIEffect {
    data class ShowToast(val message: String) : HomeUIEffect()
    data class ShowSnackBar(val message: String) : HomeUIEffect()
}
