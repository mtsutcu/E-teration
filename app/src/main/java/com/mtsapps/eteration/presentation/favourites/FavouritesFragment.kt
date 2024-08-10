package com.mtsapps.eteration.presentation.favourites

import androidx.fragment.app.viewModels
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FragmentFavouritesBinding,FavouritesUIEvent,FavouritesUIState,FavouritesUIEffect,FavouritesViewModel>(FragmentFavouritesBinding::inflate){
    override val viewModel: FavouritesViewModel by viewModels()

    override fun observeState(state: FavouritesUIState) {
    }

    override fun handleEffect(effect: FavouritesUIEffect) {
    }
}