package com.luis.dev.meliapp.core.components.searchTopAppBar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchBarViewModel : ViewModel() {

    private val _state = MutableStateFlow(SearchBarState())
    val state: StateFlow<SearchBarState> get() = _state

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
