package com.luis.dev.meliapp.features.search.presentation

/**
 * Intenciones que describen las acciones del usuario o eventos de la pantalla de búsqueda.
 */
sealed class SearchIntent {

    /**
     * Se activa cuando el usuario actualiza el texto del campo de búsqueda.
     * @property newQuery El texto de búsqueda introducido por el usuario.
     */
    data class UpdateQuery(val newQuery: String) : SearchIntent()

    /**
     * Se activa cuando el usuario presiona el botón de buscar o el ícono de buscar.
     */
    object PerformSearch : SearchIntent()
}
