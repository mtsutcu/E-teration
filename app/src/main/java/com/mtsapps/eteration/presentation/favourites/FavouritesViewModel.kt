package com.mtsapps.eteration.presentation.favourites

import androidx.lifecycle.viewModelScope
import com.mtsapps.eteration.commons.BaseViewModel
import com.mtsapps.eteration.commons.UIEffect
import com.mtsapps.eteration.commons.UIEvent
import com.mtsapps.eteration.commons.UIState
import com.mtsapps.eteration.data.local.entity.FavoriteProduct
import com.mtsapps.eteration.data.repository.FavouriteProductRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouriteProductRepositoryImpl: FavouriteProductRepositoryImpl

) : BaseViewModel<FavouritesUIEvent, FavouritesUIState, FavouritesUIEffect>() {
    override fun createInitialState(): FavouritesUIState {
        return FavouritesUIState()
    }

    override fun handleEvent(event: FavouritesUIEvent) {
        when (event) {
            is FavouritesUIEvent.OnDeleteFavourite ->{deleteFavourite(event.favoriteProduct)}
        }
    }

    override fun loadInitialData() {
        super.loadInitialData()
        getAllFavourites()
    }
   private fun getAllFavourites() {
       viewModelScope.launch {
           favouriteProductRepositoryImpl.getAllFavorites().collect{
               setState { copy(favouriteList = it) }
           }
       }
   }
    private fun deleteFavourite(favoriteProduct: FavoriteProduct){
        viewModelScope.launch {
            favouriteProductRepositoryImpl.delete(favoriteProduct.productId)
            setEffect { FavouritesUIEffect.ShowSnackBar("${favoriteProduct.name} deleted") }
        }
    }
}

data class FavouritesUIState(
    val favouriteList :List<FavoriteProduct>? = null
) :
    UIState

sealed class FavouritesUIEvent : UIEvent {
    data class OnDeleteFavourite(val favoriteProduct: FavoriteProduct) : FavouritesUIEvent()
}

sealed class FavouritesUIEffect : UIEffect {
    data class ShowSnackBar(val message: String) : FavouritesUIEffect()
}