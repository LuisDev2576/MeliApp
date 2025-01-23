package com.luis.dev.meliapp.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.search.domain.usecases.SearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "SearchViewModel"
    }

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> get() = _state

    fun handleIntent(intent: SearchIntent) {
        Log.d(TAG, "Handling intent: $intent") // Log de intención recibida
        when (intent) {
            is SearchIntent.UpdateQuery -> updateQueryText(intent.newQuery)
            is SearchIntent.PerformSearch -> performSearch()
        }
    }

    private fun updateQueryText(newQuery: String) {
        Log.d(TAG, "Updating query text to: $newQuery") // Log de actualización de query
        _state.value = _state.value.copy(query = newQuery, error = null)
    }

    private fun performSearch() {
        val query = _state.value.query
        Log.d(TAG, "Performing search with query: $query") // Log de inicio de búsqueda
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val results = searchUseCase.execute(query)
                Log.d(TAG, "Search successful. Number of results: ${results.size}") // Log de éxito
                _state.value = _state.value.copy(
                    isLoading = false,
                    results = results
                )
            } catch (e: Exception) {
                Log.e(TAG, "Search failed", e) // Log de error
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }
}
