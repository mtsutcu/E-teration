package com.mtsapps.eteration.presentation.card

import androidx.fragment.app.viewModels
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment :
    BaseFragment<FragmentCartBinding, CartUIEvent, CartUIState, CartUIEffect, CartViewModel>(FragmentCartBinding::inflate) {
    override val viewModel: CartViewModel by viewModels()


    override fun handleEffect(effect: CartUIEffect) {


    }
    override fun observeState(state: CartUIState) {

    }



}