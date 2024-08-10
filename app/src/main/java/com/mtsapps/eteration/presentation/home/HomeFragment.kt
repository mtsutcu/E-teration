package com.mtsapps.eteration.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mtsapps.eteration.R
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding,HomeUIEvent,HomeUIState,HomeUIEffect,HomeViewModel>(FragmentHomeBinding::inflate){

    override val viewModel: HomeViewModel by viewModels()

    override fun observeState(state: HomeUIState) {
    }

    override fun handleEffect(effect: HomeUIEffect) {

    }

}