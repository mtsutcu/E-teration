package com.mtsapps.eteration.presentation.home

import ProductLoadStateAdapter
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mtsapps.eteration.R
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.commons.utils.GridSpacingItemDecoration
import com.mtsapps.eteration.commons.utils.changeVisibility
import com.mtsapps.eteration.commons.utils.clickWithDebounce
import com.mtsapps.eteration.commons.utils.hideKeyboard
import com.mtsapps.eteration.databinding.FragmentHomeBinding
import com.mtsapps.eteration.presentation.filter.FilterBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeUIEvent, HomeUIState, HomeUIEffect, HomeViewModel>(
        FragmentHomeBinding::inflate
    ) {

    override val viewModel: HomeViewModel by activityViewModels()

    override fun handleEffect(effect: HomeUIEffect) {
        when (effect) {
            is HomeUIEffect.ShowToast -> {
                Toast.makeText(requireContext(), "${effect.message} ${resources.getText(R.string.added)}", Toast.LENGTH_SHORT).show()
            }

            is HomeUIEffect.ShowSnackBar -> {
                val snackBar = Snackbar.make(binding.root, effect.message, Snackbar.LENGTH_SHORT)
                snackBar.show()
            }
        }
    }

    override fun setupUI() {
        super.setupUI()
        viewModel.setEvent(HomeUIEvent.OnGetAllProducts)
        val homeAdapter =
            HomeProductsAdapter(requireContext(),onAddToCartClick = { product ->
                viewModel.setEvent(HomeUIEvent.OnAddCartEntity(product))
            }) { product ->
                val action =
                    HomeFragmentDirections.actionNavigationHomeFragmentToDetailFragment(product)
                findNavController().navigate(action)
            }
        val recyclerView = binding.homeRecyclerview
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = homeAdapter.getItemViewType(position)
                return if (viewType == HomeProductsAdapter.LOADING_VIEW_TYPE) 1
                else 2
            }
        }

        recyclerView.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpacingItemDecoration(16, requireContext()))
            adapter =
                homeAdapter.withLoadStateFooter(footer = ProductLoadStateAdapter { homeAdapter.retry() })
                setHasFixedSize(true)
        }
       viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                homeAdapter.submitData(it.productList)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            homeAdapter.loadStateFlow.collectLatest { loadState ->
                when {
                    loadState.refresh is LoadState.Error -> {
                        viewModel.setEvent(HomeUIEvent.OnOpeningError)
                    }

                    loadState.append is LoadState.Error -> {
                        viewModel.setEvent(HomeUIEvent.OnSetLoadingError((loadState.append as LoadState.Error).error))
                    }

                    loadState.prepend is LoadState.Error -> {
                        viewModel.setEvent(HomeUIEvent.OnSetLoadingError((loadState.prepend as LoadState.Error).error))
                    }
                }
            }
        }

    }

    override fun observeState(state: HomeUIState) {
        binding.apply {
            homeProgress.changeVisibility(state.isLoading)
            homeRecyclerview.changeVisibility(!state.isLoading)
            homeTryAgainButton.apply {
                changeVisibility(state.isError)
                clickWithDebounce {
                    changeVisibility(false)
                    viewModel.setEvent(HomeUIEvent.OnTryAgain)
                }
            }
            homeSearchbar.apply {
                setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.setEvent(HomeUIEvent.OnSetFilterText(v.text.toString()))
                        clearFocus()
                        hideKeyboard()
                        return@OnEditorActionListener true
                    }
                    false
                })
            }
            homeSelectFilterText.clickWithDebounce {
                val filterBottomSheet = FilterBottomSheetFragment()
                filterBottomSheet.show(parentFragmentManager, "FilterBottomSheet")

            }

        }

    }
}