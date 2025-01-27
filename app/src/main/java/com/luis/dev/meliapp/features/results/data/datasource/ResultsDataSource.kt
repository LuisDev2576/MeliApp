package com.luis.dev.meliapp.features.results.data.datasource

import SearchResponse

/**
 * Interfaz que define las operaciones necesarias para obtener los resultados de búsqueda de artículos.
 */
interface ResultsDataSource {

    /**
     * Realiza una búsqueda de artículos en función del sitio y la consulta proporcionados.
     *
     * @param siteId Identificador del sitio en el que se realizará la búsqueda (por ejemplo, "MLA" para Argentina).
     * @param query Palabra clave o frase que define la búsqueda de los artículos.
     * @return [SearchResponse] con los resultados de la búsqueda.
     */
    suspend fun searchItems(siteId: String, query: String): SearchResponse
}
