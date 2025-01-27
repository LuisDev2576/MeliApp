package com.luis.dev.meliapp.features.results.domain.usecases

import SearchResultItem
import com.luis.dev.meliapp.features.results.data.repository.ResultsRepository

/**
 * Implementación de [GetResultsUseCase] que utiliza un repositorio para obtener los resultados de búsqueda.
 *
 * @param repository Repositorio que maneja la lógica de acceso a los datos de los resultados de búsqueda.
 */
class GetResultsUseCaseImpl(
    private val repository: ResultsRepository
) : GetResultsUseCase {

    /**
     * Ejecuta la búsqueda de artículos basada en la consulta proporcionada.
     *
     * @param query Palabra clave o frase que define la búsqueda de los artículos.
     * @return Lista de [SearchResultItem] que contiene los resultados de la búsqueda.
     */
    override suspend fun execute(query: String): List<SearchResultItem> {
        return repository.searchItems(query).results
    }
}
