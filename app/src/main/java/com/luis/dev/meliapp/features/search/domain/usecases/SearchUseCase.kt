package com.luis.dev.meliapp.features.search.domain.usecases

import SearchResultItem

/**
 * Caso de uso para buscar ítems en Mercado Libre.
 */
interface SearchUseCase {

    /**
     * Ejecuta la búsqueda de productos dada una query.
     * @param query El texto que el usuario ingresó en el buscador.
     * @return Lista de [SearchResultItem] con información básica del producto.
     */
    suspend fun execute(query: String): List<SearchResultItem>
}
