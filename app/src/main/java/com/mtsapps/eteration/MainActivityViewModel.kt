package com.mtsapps.eteration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtsapps.eteration.domain.use_cases.GetAllCartEnititiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val getAllCartEnititiesUseCase: GetAllCartEnititiesUseCase) :
    ViewModel() {
       private val _badgeCount = MutableStateFlow(0)
        var badgeCount = _badgeCount.asStateFlow()

    init {
    getBadgeCount()
    }

    private fun getBadgeCount(){
        viewModelScope.launch {
            getAllCartEnititiesUseCase().collect{cartEntities->
                _badgeCount.update {
                    cartEntities.size
                }
            }
        }
    }
}