package com.luis.dev.meliapp.features.details.domain.usecases

import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse

/**
 * Caso de uso para obtener el detalle de un producto dado su ID.
 */
interface GetItemDetailUseCase {

    /**
     * Ejecuta la obtención del detalle del producto.
     * @param itemId Identificador único del producto.
     * @return [ItemDetailResponse] con la información del producto.
     */
    suspend fun execute(itemId: String): ItemDetailResponse
}