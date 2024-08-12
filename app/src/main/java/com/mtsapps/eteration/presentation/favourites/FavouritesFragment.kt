package com.mtsapps.eteration.presentation.favourites

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mtsapps.eteration.R
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.commons.utils.LinearSpacingItemDecoration
import com.mtsapps.eteration.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FragmentFavouritesBinding,FavouritesUIEvent,FavouritesUIState,FavouritesUIEffect,FavouritesViewModel>(FragmentFavouritesBinding::inflate){
    private lateinit var favouriteAdapter: FavouriteProductAdapter
    override val viewModel: FavouritesViewModel by viewModels()

    override fun observeState(state: FavouritesUIState) {
            state.favouriteList?.let {
                favouriteAdapter.submitList(state.favouriteList)
            }
    }

    override fun handleEffect(effect: FavouritesUIEffect) {
        when(effect){
            is FavouritesUIEffect.ShowSnackBar->{
                Snackbar.make(binding.root,"${effect.message} ${resources.getText(R.string.deleted)}",Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun setupUI() {
        super.setupUI()
        viewModel.setEvent(FavouritesUIEvent.OnGetAllFavourites)
        favouriteAdapter = FavouriteProductAdapter {
            viewModel.setEvent(FavouritesUIEvent.OnDeleteFavourite(it))
        }
        binding.favouriteRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(LinearSpacingItemDecoration(16,requireContext(),true))
            adapter = favouriteAdapter
        }
    }
}