package com.mtsapps.eteration.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mtsapps.eteration.R
import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import com.mtsapps.eteration.commons.utils.Constants
import com.mtsapps.eteration.data.repository.FavouriteProductRepositoryImpl
import com.mtsapps.eteration.data.repository.ProductRepositoryImpl
import com.mtsapps.eteration.domain.models.Product
import com.mtsapps.eteration.domain.use_cases.InsertCartEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepositoryImpl: ProductRepositoryImpl,
    private val insertCartEntityUseCase: InsertCartEntityUseCase,
    private val favouriteProductRepositoryImpl: FavouriteProductRepositoryImpl
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
            is HomeUIEvent.OnSetSortFilterId -> setSortFilterId(event.id)
            is HomeUIEvent.OnSetBrandFilter -> setBrandFilter(event.brandList)
            is HomeUIEvent.OnSetModelFilter -> setModelFilter(event.modelList)
            is HomeUIEvent.OnAddFilters -> loadInitialData()

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
            delay(1000)
            productRepositoryImpl.getProducts(
                filter = uiState.value.searchFilterText,
                sortedBy = handleSelectedSortedBy(uiState.value.sortFilterId),
                brand = handleSelectedFiltersList(uiState.value.brandFilterText),
                model =handleSelectedFiltersList( uiState.value.modelFilterText),
                order = handleSelectedSortedOrder(uiState.value.sortFilterId)
            ).flow.cachedIn(viewModelScope).combine(favouriteProductRepositoryImpl.getAllFavorites()) { pagingData, favoriteProducts ->
                    pagingData.map { product ->
                        val isFavorite = favoriteProducts.any { it.productId == product.id }
                        product.copy(isFav = isFavorite)
                    }
                }
                .collectLatest{
                    setState { copy(productList = it, isLoading = false, isError = false) }
                }
        }
    }

    private fun tryAgain() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            delay(1000)
            productRepositoryImpl.getProducts(
                filter = uiState.value.searchFilterText,
                sortedBy = handleSelectedSortedBy(uiState.value.sortFilterId),
                brand = handleSelectedFiltersList(uiState.value.brandFilterText),
                model =handleSelectedFiltersList( uiState.value.modelFilterText),
                order = handleSelectedSortedOrder(uiState.value.sortFilterId)
            ).flow.cachedIn(viewModelScope).combine(favouriteProductRepositoryImpl.getAllFavorites()) { pagingData, favoriteProducts ->
                pagingData.map { product ->
                    val isFavorite = favoriteProducts.any { it.productId == product.id }
                    product.copy(isFav = isFavorite)
                }
            }
                .collectLatest{
                    setState { copy(productList = it, isLoading = false, isError = false) }
                }
        }
    }

    private fun setFilterText(text: String) {
        setState { copy(searchFilterText = text)}
        getProducts()
    }

    private fun addCartEntity(product: Product) {
        viewModelScope.launch {
            insertCartEntityUseCase(product)
        }
        setEffect { HomeUIEffect.ShowSnackBar(message = "${product.name}")}
    }

    private fun setSortFilterId(id: Int) {
        setState { copy(sortFilterId = id) }
    }

    private fun setBrandFilter(brandList: List<String>) {
        setState { copy(brandFilterText = brandList) }
    }

    private fun setModelFilter(modelList: List<String>) {
        setState { copy(modelFilterText = modelList) }
    }

    private fun handleSelectedSortedOrder(id: Int): String {
        return when (id) {
            R.id.filter_old_to_new -> "asc"
            R.id.filter_new_to_old -> "desc"
            R.id.filter_price_low_to_high -> "asc"
            R.id.filter_price_high_to_low -> "desc"
            else -> {
                "asc"
            }
        }
    }

    private fun handleSelectedSortedBy(id: Int): String {
        return when (id) {
            R.id.filter_old_to_new -> "createdAt"
            R.id.filter_new_to_old -> "createdAt"
            R.id.filter_price_low_to_high -> "price"
            R.id.filter_price_high_to_low -> "price"
            else -> {
                "createdAt"
            }
        }
    }
    private fun handleSelectedFiltersList(selectedList : List<String>):String{
        return if (selectedList.isNotEmpty()){
            selectedList.joinToString (separator = "|")
        }else{
            ""
        }
    }

}

data class HomeUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val productList: PagingData<Product> = PagingData.empty(),
    val searchFilterText: String = "",
    val sortFilterId: Int = R.id.filter_old_to_new,
    val brandFilterText: List<String> = emptyList(),
    val modelFilterText: List<String> = emptyList(),
    val brandsFilterList: List<String> = Constants.brands,
    val modelsFilterList: List<String> = Constants.models
) :
    UIState

sealed class HomeUIEvent : UIEvent {
    data object OnOpeningError : HomeUIEvent()
    data class OnSetLoadingError(val e: Throwable) : HomeUIEvent()
    data object OnTryAgain : HomeUIEvent()
    data class OnSetFilterText(val text: String) : HomeUIEvent()
    data class OnAddCartEntity(val product: Product) : HomeUIEvent()
    data class OnSetSortFilterId(val id: Int) : HomeUIEvent()
    data class OnSetBrandFilter(val brandList: List<String>) : HomeUIEvent()
    data class OnSetModelFilter(val modelList: List<String>) : HomeUIEvent()
    data object OnAddFilters : HomeUIEvent()



}

sealed class HomeUIEffect : UIEffect {
    data class ShowToast(val message: String) : HomeUIEffect()
    data class ShowSnackBar(val message: String) : HomeUIEffect()
}
