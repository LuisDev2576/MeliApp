package com.luis.dev.meliapp.features.results.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.results.domain.usecases.GetResultsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResultsViewModel(
    private val getResultsUseCase: GetResultsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val product: String = savedStateHandle["productName"] ?:
    throw IllegalArgumentException("Se requiero un producto para realizar la busqueda")

    private val _state = MutableStateFlow(ResultsState())
    val state: StateFlow<ResultsState> get() = _state

    init {
        fetchResultsFor()
    }

    fun fetchResultsFor() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val results = getResultsUseCase.execute(product)
                _state.value = _state.value.copy(
                    isLoading = false,
                    results = results
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }
}
