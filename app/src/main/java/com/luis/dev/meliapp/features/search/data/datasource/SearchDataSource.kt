package com.luis.dev.meliapp.features.search.data.datasource

import SearchResponse

/**
 * Fuente de datos encargada de realizar la búsqueda de ítems en la API de Mercado Libre.
 */
interface SearchDataSource {

    /**
     * Realiza una búsqueda de ítems en Mercado Libre.
     * @param siteId Código del sitio (por ejemplo "MLA" para Argentina).
     * @param query Término de búsqueda escrito por el usuario.
     * @return Retorna la respuesta del servidor con los ítems encontrados.
     */
    suspend fun searchItems(siteId: String, query: String): SearchResponse
}
