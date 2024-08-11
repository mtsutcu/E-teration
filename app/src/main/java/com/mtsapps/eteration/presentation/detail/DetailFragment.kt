package com.mtsapps.eteration.presentation.detail


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.commons.utils.getImageFromUrl
import com.mtsapps.eteration.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding,DetailUIEvent,DetailUIState,DetailUIEffect,DetailViewModel>(FragmentDetailBinding::inflate) {
    override val viewModel: DetailViewModel by viewModels()

    override fun handleEffect(effect: DetailUIEffect) {
    }

    override fun observeState(state: DetailUIState) {
    }

    override fun setupUI() {
        super.setupUI()
        val product = arguments?.let { DetailFragmentArgs.fromBundle(it).product }
        binding.apply {
            productDetailToolbar.apply {
                title = product?.name
                setNavigationOnClickListener {
                    findNavController().navigateUp()
                }
            }
            productDetailPriceText.text = product?.price
            productDetailName.text = product?.name
            productDetailDescription.text = product?.description
            productDetailImage.getImageFromUrl(product?.image)
        }
    }

}