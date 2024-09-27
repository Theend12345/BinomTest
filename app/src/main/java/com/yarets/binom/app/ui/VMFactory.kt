package com.yarets.binom.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yarets.binom.domain.GetPersonsUsecase
import javax.inject.Inject

class VMFactory @Inject constructor(val getPersonsUsecase: GetPersonsUsecase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapViewModel(getPersonsUsecase) as T
    }
}