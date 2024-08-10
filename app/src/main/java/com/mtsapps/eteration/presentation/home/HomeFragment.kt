package com.mtsapps.eteration.presentation.home

import androidx.fragment.app.viewModels
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeUIEvent,HomeUIState,HomeUIEffect,HomeViewModel>(FragmentHomeBinding::inflate){

    override val viewModel: HomeViewModel by viewModels()

    override fun observeState(state: HomeUIState) {
    }

    override fun handleEffect(effect: HomeUIEffect) {

    }

}