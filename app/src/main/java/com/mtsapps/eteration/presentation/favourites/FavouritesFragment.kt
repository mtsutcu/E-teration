package com.mtsapps.eteration.presentation.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mtsapps.eteration.R
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.databinding.FragmentFavouritesBinding
import com.mtsapps.eteration.databinding.FragmentHomeBinding

class FavouritesFragment : BaseFragment<FragmentFavouritesBinding,FavouritesUIEvent,FavouritesUIState,FavouritesUIEffect,FavouritesViewModel>(FragmentFavouritesBinding::inflate){
    override val viewModel: FavouritesViewModel by viewModels()

    override fun observeState(state: FavouritesUIState) {
    }

    override fun handleEffect(effect: FavouritesUIEffect) {
    }
}