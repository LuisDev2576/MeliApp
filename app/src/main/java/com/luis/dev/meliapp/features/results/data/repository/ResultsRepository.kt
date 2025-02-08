package com.luis.dev.meliapp.features.results.data.repository

import SearchResponse

/**
 * Interfaz que define las operaciones necesarias para interactuar con los datos relacionados a los resultados de búsqueda.
 */
interface ResultsRepository {

    /**
     * Busca artículos basados en la consulta proporcionada.
     *
     * @param query Palabra clave o frase que define la búsqueda de los artículos.
     * @return [SearchResponse] que contiene los resultados de la búsqueda.
     */
    suspend fun searchItems(query: String): SearchResponse
}