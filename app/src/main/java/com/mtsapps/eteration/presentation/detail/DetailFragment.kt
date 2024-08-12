package com.mtsapps.eteration.presentation.detail


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mtsapps.eteration.commons.BaseFragment
import com.mtsapps.eteration.commons.utils.clickWithDebounce
import com.mtsapps.eteration.commons.utils.getImageFromUrl
import com.mtsapps.eteration.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding,DetailUIEvent,DetailUIState,DetailUIEffect,DetailViewModel>(FragmentDetailBinding::inflate) {
    override val viewModel: DetailViewModel by viewModels()


    override fun handleEffect(effect: DetailUIEffect) {
        when(effect){
            is DetailUIEffect.ShowSnackBar->{
                Snackbar.make(binding.root,effect.message,Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun observeState(state: DetailUIState) {
        binding.productDetailFavouriteStar.isSelected = state.isFav
        binding.productDetailFavouriteStar.apply {
            clickWithDebounce {
                if (state.isFav){
                    viewModel.setEvent(DetailUIEvent.OnDeleteFavourite)
                }else{
                    viewModel.setEvent(DetailUIEvent.OnAddFavourite)
                }
            }

        }
    }

    override fun setupUI() {
        super.setupUI()
        val product = arguments?.let { DetailFragmentArgs.fromBundle(it).product }
        viewModel.setEvent(DetailUIEvent.OnSetProduct(product!!))
        binding.apply {
            productDetailToolbar.apply {
                title = product.brand
            }
            productDetailPriceText.text = product.price
            productDetailName.text = product.brand
            productDetailDescription.text = product.description
            productDetailImage.getImageFromUrl(product.image)
        }
    }

    override fun setupListeners() {
        binding.apply {
            productDetailToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            productDetailButton.clickWithDebounce {
                viewModel.setEvent(DetailUIEvent.OnAddCartEntity)            }
        }
    }

}