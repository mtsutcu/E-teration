package com.mtsapps.eteration.presentation.cart

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.commons.utils.LinearSpacingItemDecoration
import com.mtsapps.eteration.commons.utils.changeVisibility
import com.mtsapps.eteration.commons.utils.clickWithDebounce
import com.mtsapps.eteration.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment :
    BaseFragment<FragmentCartBinding, CartUIEvent, CartUIState, CartUIEffect, CartViewModel>(
        FragmentCartBinding::inflate
    ) {
    override val viewModel: CartViewModel by viewModels()


    override fun handleEffect(effect: CartUIEffect) {
        when (effect) {
            is CartUIEffect.ShowSnackBar -> {
                Snackbar.make(binding.root, effect.message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun observeState(state: CartUIState) {
        val cartAdapter = CartAdapter(minusOnClick = {
            viewModel.setEvent(CartUIEvent.OnMinusCartCount(it))
        }, plusOnClick = {
            viewModel.setEvent(CartUIEvent.OnPlusCartCount(it))
        })

        state.cartList?.let {
            cartAdapter.submitList(state.cartList)
        }
        binding.apply {
            cartTotalPriceText.text = state.totalPrice.toString()
            cartCompleteButton.changeVisibility(!state.cartList.isNullOrEmpty())
            cartRecyclerview.adapter = cartAdapter
        }
    }

    override fun setupUI() {
        super.setupUI()
        binding.apply {
            cartRecyclerview.apply {
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(LinearSpacingItemDecoration(16, requireContext(), false))
            }
            cartCompleteButton.clickWithDebounce {
                viewModel.setEvent(CartUIEvent.OnCompleteCart)
            }
        }
    }


}