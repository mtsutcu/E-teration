package com.mtsapps.eteration.presentation.profile

import androidx.fragment.app.viewModels
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding,ProfileUIEvent,ProfileUIState,ProfileUIEffect,ProfileViewModel>(FragmentProfileBinding::inflate){
    override val viewModel: ProfileViewModel by viewModels()

    override fun handleEffect(effect: ProfileUIEffect) {

    }

    override fun observeState(state: ProfileUIState) {
    }
}