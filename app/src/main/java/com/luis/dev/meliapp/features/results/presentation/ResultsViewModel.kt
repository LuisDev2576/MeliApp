package com.luis.dev.meliapp.features.results.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.results.domain.usecases.GetResultsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para manejar el estado y la lógica de la pantalla de resultados de búsqueda.
 *
 * @param getResultsUseCase Caso de uso para obtener los resultados de búsqueda.
 * @param savedStateHandle Contenedor para los datos guardados en el estado de la vista, incluye el nombre del producto.
 */
class ResultsViewModel(
    private val getResultsUseCase: GetResultsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    /**
     * Producto a buscar, obtenido del [SavedStateHandle].
     * Lanza una excepción si no está presente.
     */
    private val product: String = savedStateHandle["productName"]
        ?: throw IllegalArgumentException("Se requiere un producto para realizar la búsqueda")

    /**
     * Estado interno mutable que representa el estado actual de los resultados de búsqueda.
     */
    private val _state = MutableStateFlow(ResultsState())

    /**
     * Estado público inmutable que expone los resultados de búsqueda, el estado de carga y posibles errores.
     */
    val state: StateFlow<ResultsState> get() = _state

    /**
     * Inicializa el ViewModel buscando los resultados para el producto especificado.
     */
    init {
        fetchResultsFor()
    }

    /**
     * Obtiene los resultados de búsqueda para el producto almacenado en el estado.
     * Actualiza el estado con los resultados obtenidos, el progreso de la carga o errores.
     */
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
