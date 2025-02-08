package com.luis.dev.meliapp.features.results.domain.usecases

import SearchResultItem

/**
 * Interfaz que define el caso de uso para obtener los resultados de búsqueda.
 */
interface GetResultsUseCase {

    /**
     * Ejecuta la búsqueda de artículos basada en la consulta proporcionada.
     *
     * @param query Palabra clave o frase que define la búsqueda de los artículos.
     * @return Lista de [SearchResultItem] que contiene los resultados de la búsqueda.
     */
    suspend fun execute(query: String): List<SearchResultItem>
}