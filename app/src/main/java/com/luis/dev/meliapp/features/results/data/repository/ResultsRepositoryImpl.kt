package com.luis.dev.meliapp.features.results.data.repository

import SearchResponse
import com.luis.dev.meliapp.features.results.data.datasource.ResultsDataSource

/**
 * Implementación de [ResultsRepository] que interactúa con un [ResultsDataSource]
 * para obtener datos relacionados con los resultados de búsqueda.
 *
 * @param dataSource Fuente de datos que realiza las llamadas a la API.
 * @param defaultSiteId Identificador del sitio por defecto donde se realizará la búsqueda (por ejemplo, "MLA" para Argentina).
 */
class ResultsRepositoryImpl(
    private val dataSource: ResultsDataSource,
    private val defaultSiteId: String = "MLA"
) : ResultsRepository {

    /**
     * Busca artículos basados en la consulta proporcionada, utilizando un sitio predeterminado.
     *
     * @param query Palabra clave o frase que define la búsqueda de los artículos.
     * @return [SearchResponse] con los resultados de la búsqueda.
     */
    override suspend fun searchItems(query: String): SearchResponse {
        return dataSource.searchItems(defaultSiteId, query)
    }
}