package com.mtsapps.eteration.presentation.card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mtsapps.eteration.R
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.databinding.FragmentCartBinding
import com.mtsapps.eteration.databinding.FragmentHomeBinding

class CartFragment :
    BaseFragment<FragmentCartBinding, CartUIEvent, CartUIState, CartUIEffect, CartViewModel>(FragmentCartBinding::inflate) {
    override val viewModel: CartViewModel by viewModels()


    override fun handleEffect(effect: CartUIEffect) {


    }
    override fun observeState(state: CartUIState) {

    }



}