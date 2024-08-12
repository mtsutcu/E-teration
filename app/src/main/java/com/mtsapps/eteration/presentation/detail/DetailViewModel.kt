package com.mtsapps.eteration.presentation.detail

import androidx.lifecycle.viewModelScope
import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import com.mtsapps.eteration.data.local.entity.FavoriteProduct
import com.mtsapps.eteration.data.repository.FavouriteProductRepositoryImpl
import com.mtsapps.eteration.domain.models.Product
import com.mtsapps.eteration.domain.use_cases.InsertCartEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
private val insertCartEntityUseCase: InsertCartEntityUseCase,
private val favouriteProductRepositoryImpl: FavouriteProductRepositoryImpl
) : BaseViewModel<DetailUIEvent, DetailUIState, DetailUIEffect>() {
    override fun createInitialState(): DetailUIState {
        return DetailUIState()
    }


    override fun handleEvent(event: DetailUIEvent) {
        when (event) {
            is DetailUIEvent.OnAddCartEntity -> addCartEntity()
            is DetailUIEvent.OnAddFavourite -> addFavourite()
            is DetailUIEvent.OnDeleteFavourite -> deleteFavourite()
            is DetailUIEvent.OnCheckFavourite -> isFav()
            is DetailUIEvent.OnSetProduct -> setProduct(event.product)
        }
    }

   private fun addCartEntity() {
       viewModelScope.launch {
           uiState.value.product?.let { insertCartEntityUseCase(it)
               setEffect { DetailUIEffect.ShowSnackBarCart("${it.brand}") }
           }
       }
   }
    private fun addFavourite(){
        viewModelScope.launch {
            favouriteProductRepositoryImpl.insert(FavoriteProduct(productId = uiState.value.product?.id!!, image = uiState.value.product?.image!!, price = uiState.value.product?.price!!, name = uiState.value.product?.brand!!))
            setEffect { DetailUIEffect.ShowSnackBarFav("${uiState.value.product?.brand}") }

        }
    }
    private fun deleteFavourite(){
        viewModelScope.launch {
            favouriteProductRepositoryImpl.delete(uiState.value.product?.id.toString())
        }
    }
    private fun isFav(){
        viewModelScope.launch {
            uiState.value.product?.id?.let {
                favouriteProductRepositoryImpl.isFavorite(it).collect{favProduct->
                    if (favProduct != null){
                        setState { copy(isFav = true) }
                    }else{
                        setState { copy(isFav = false) }

                    }
                }
            }
        }
    }
    private fun setProduct(product: Product){
        setState { copy(product=product) }
        isFav()
    }
}

data class DetailUIState(
    val isFav : Boolean = false,
    val product: Product? = null
) :
    UIState

sealed class DetailUIEvent : UIEvent {
    data object OnAddCartEntity : DetailUIEvent()
    data object OnAddFavourite : DetailUIEvent()
    data object OnDeleteFavourite : DetailUIEvent()
    data object OnCheckFavourite : DetailUIEvent()
    data class OnSetProduct(val product: Product) : DetailUIEvent()
}
sealed class DetailUIEffect : UIEffect {
    data class ShowSnackBarCart(val message: String) : DetailUIEffect()
    data class ShowSnackBarFav(val message: String) : DetailUIEffect()
}