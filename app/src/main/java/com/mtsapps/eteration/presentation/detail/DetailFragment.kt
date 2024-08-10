package com.mtsapps.eteration.presentation.detail

import androidx.fragment.app.viewModels
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding,DetailUIEvent,DetailUIState,DetailUIEffect,DetailViewModel>(FragmentDetailBinding::inflate) {
    override val viewModel: DetailViewModel by viewModels()

    override fun handleEffect(effect: DetailUIEffect) {
    }

    override fun observeState(state: DetailUIState) {
    }


}