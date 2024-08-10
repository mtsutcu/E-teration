package com.mtsapps.eteration.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mtsapps.eteration.R
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.databinding.FragmentHomeBinding
import com.mtsapps.eteration.databinding.FragmentProfileBinding


class ProfileFragment : BaseFragment<FragmentProfileBinding,ProfileUIEvent,ProfileUIState,ProfileUIEffect,ProfileViewModel>(FragmentProfileBinding::inflate){
    override val viewModel: ProfileViewModel by viewModels()

    override fun handleEffect(effect: ProfileUIEffect) {

    }

    override fun observeState(state: ProfileUIState) {
    }
}