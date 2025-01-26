package com.luis.dev.meliapp.features.details.domain.usecases

import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse
import com.luis.dev.meliapp.features.details.data.repository.DetailsRepository

/**
 * Implementación de [GetItemDetailUseCase].
 * @property repository Repositorio para obtener la información del producto.
 */
class GetItemDetailUseCaseImpl(
    private val repository: DetailsRepository
) : GetItemDetailUseCase {

    /**
     * Ejecuta la obtención del detalle del producto llamando al repositorio.
     * @param itemId Identificador único del producto.
     * @return [ItemDetailResponse] con la información del producto.
     */
    override suspend fun execute(itemId: String): ItemDetailResponse {
        return repository.getItemDetail(itemId)
    }
}
