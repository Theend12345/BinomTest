package com.yarets.binom.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarets.binom.app.model.Person
import com.yarets.binom.app.util.UiState
import com.yarets.binom.app.util.toPerson
import com.yarets.binom.domain.GetPersonsUsecase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(
    val getPersonsUsecase: GetPersonsUsecase
) : ViewModel() {
    private var _personListResponse: MutableStateFlow<UiState<List<Person>>> =
        MutableStateFlow(UiState.Default)
    val personListResponse: StateFlow<UiState<List<Person>>>
        get() = _personListResponse.asStateFlow()

    private var _personResponse: MutableSharedFlow<Person> = MutableSharedFlow()
    val personResponse: SharedFlow<Person>
        get() = _personResponse.asSharedFlow()

    private fun fetchPersonList() {
        viewModelScope.launch {
            getPersonsUsecase().collect { list ->
                try {
                    _personListResponse.value = UiState.Success(list.map { it.toPerson() })
                } catch (e: Throwable) {
                    _personListResponse.value = UiState.Error(e)
                }
            }
        }
    }

    fun fetchPersonById(id: Int) {
        viewModelScope.launch {
            getPersonsUsecase(id).collect {
                _personResponse.emit(it.toPerson())
            }
        }
    }

    init {
        fetchPersonList()
    }
}