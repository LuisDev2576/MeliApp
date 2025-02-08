package com.luis.dev.meliapp.core.components.searchTopAppBar

sealed class SearchBarIntent {
    data class UpdateQuery(val newQuery: String) : SearchBarIntent()
    object ClearQuery : SearchBarIntent()
}