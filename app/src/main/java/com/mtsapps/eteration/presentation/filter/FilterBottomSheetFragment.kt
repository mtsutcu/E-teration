package com.mtsapps.eteration.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtsapps.eteration.R
import com.mtsapps.eteration.commons.utils.clickWithDebounce
import com.mtsapps.eteration.commons.utils.hideKeyboard
import com.mtsapps.eteration.databinding.FragmentFilterBottomSheetBinding
import com.mtsapps.eteration.presentation.home.HomeUIEvent
import com.mtsapps.eteration.presentation.home.HomeViewModel
import kotlinx.coroutines.launch


class FilterBottomSheetFragment : BottomSheetDialogFragment() {
    private val viewModel: HomeViewModel by activityViewModels()
    private var selecedBrands = emptyList<String>()
    private var selecedModels = emptyList<String>()
    private var selecedSortedBy = R.id.filter_old_to_new
    private val  brandAdapter= FilterAdapter{
        selecedBrands = it
    }
    private val modelAdapter = FilterAdapter{
        selecedModels = it
    }


    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
       selecedBrands = viewModel.uiState.value.brandFilterText
        selecedModels = viewModel.uiState.value.modelFilterText
        selecedSortedBy = viewModel.uiState.value.sortFilterId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheetBehavior = BottomSheetBehavior.from(this.view?.parent as View)
        bottomSheetBehavior.isDraggable = false

        brandAdapter.submitList(viewModel.uiState.value.brandsFilterList)
        modelAdapter.submitList(viewModel.uiState.value.modelsFilterList)
        binding.apply {
            filterToolbar.setNavigationOnClickListener {
                dismiss()
            }
            filterBrandRecyclerview.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = brandAdapter
            }
            filterModelRecyclerview.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = modelAdapter
            }


            filterRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                selecedSortedBy = checkedId
            }
                filterBrandSearchbar.apply {
                    addTextChangedListener {editable->
                        brandAdapter.submitList(viewModel.uiState.value.brandsFilterList.filter { it.contains(editable.toString()) })
                    }
                    setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            clearFocus()
                            hideKeyboard()
                            return@OnEditorActionListener true
                        }
                        false
                    })
                }
                filterModelSearchbar.apply {
                    addTextChangedListener {
                        filterList(modelAdapter,it.toString(),viewModel.uiState.value.modelsFilterList)
                    }
                    setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            clearFocus()
                            hideKeyboard()
                            return@OnEditorActionListener true
                        }
                        false
                    })
                }
            filterPrimaryButton.clickWithDebounce {
                viewModel.setEvent(HomeUIEvent.OnSetModelFilter(selecedModels))
                viewModel.setEvent(HomeUIEvent.OnSetBrandFilter(selecedBrands))
                viewModel.setEvent(HomeUIEvent.OnSetSortFilterId(selecedSortedBy))
                viewModel.setEvent(HomeUIEvent.OnAddFilters)
                dismiss()

            }
            }
        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.uiState.collect{state->
                binding.filterRadioGroup.check(state.sortFilterId)
                brandAdapter.setSelectedList(state.brandFilterText)
                modelAdapter.setSelectedList(state.modelFilterText)
            }
            }
        }

    private fun filterList(adapter: FilterAdapter,text: String, list: List<String>)
    {
         if (text.isNotEmpty()) {
            adapter.submitList(list.filter { it.contains(text) })
        } else {
            adapter.submitList(list)
         }
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            (dialog as BottomSheetDialog?)!!.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        if (bottomSheet != null) {
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
            val layoutParams = bottomSheet.layoutParams
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            bottomSheet.layoutParams = layoutParams
        }
        /* dialog!!.window!!.setFlags(
             WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
             WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
         )*/
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}