package com.luis.dev.meliapp.features.search.data.repository

import SearchResponse

/**
 * Repositorio para manejar la lógica de obtención de datos en la búsqueda.
 * Hace de intermediario entre la capa de datos (DataSource) y la capa de dominio.
 */
interface SearchRepository {

    /**
     * Busca ítems en la plataforma de Mercado Libre según la query dada.
     * @param query El término de búsqueda.
     * @return Respuesta con los ítems encontrados.
     */
    suspend fun searchItems(query: String): SearchResponse
}
