package com.luis.dev.meliapp.features.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.details.domain.usecases.GetItemDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para manejar la lógica de la lista de resultados y la carga del detalle de un producto.
 * @property getItemDetailUseCase Caso de uso para obtener el detalle de un producto.
 */
class DetailsViewModel(
    private val getItemDetailUseCase: GetItemDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: String = savedStateHandle["productId"]
        ?: throw IllegalArgumentException("Se requiero un producto para realizar la busqueda")

    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> get() = _state

    init {
        loadItemDetail(productId)
    }

    /**
     * Carga el detalle del producto y actualiza el estado.
     * @param productId Identificador único del producto.
     */
    private fun loadItemDetail(productId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null, itemDetail = null)
            try {
                val detail = getItemDetailUseCase.execute(productId)
                _state.value = _state.value.copy(isLoading = false, itemDetail = detail)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}