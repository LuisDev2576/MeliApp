package com.luis.dev.meliapp.core.components.searchTopAppBar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel para gestionar el estado y la lógica de la barra de búsqueda.
 */
class SearchBarViewModel : ViewModel() {

    private val _state = MutableStateFlow(SearchBarState())

    /**
     * Estado expuesto como un [StateFlow] inmutable para observar cambios en la vista.
     */
    val state: StateFlow<SearchBarState> get() = _state

    /**
     * Maneja las intenciones relacionadas con la barra de búsqueda.
     *
     * @param intent Representa una acción que se desea realizar en la barra de búsqueda,
     * como actualizar la consulta o limpiarla.
     */
    fun handleIntent(intent: SearchBarIntent) {
        when (intent) {
            is SearchBarIntent.UpdateQuery -> {
                _state.value = _state.value.copy(query = intent.newQuery)
            }
            SearchBarIntent.ClearQuery -> {
                _state.value = _state.value.copy(query = "")
            }
        }
    }
}