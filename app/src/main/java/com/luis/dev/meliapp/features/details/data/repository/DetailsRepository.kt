package com.luis.dev.meliapp.features.details.data.repository

import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse

/**
 * Repositorio que provee métodos para obtener el detalle de un producto.
 */
interface DetailsRepository {

    /**
     * Obtiene el detalle de un producto según su ID.
     * @param itemId Identificador único del producto.
     * @return [ItemDetailResponse] con la información del producto.
     */
    suspend fun getItemDetail(itemId: String): ItemDetailResponse
}