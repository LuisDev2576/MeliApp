package com.luis.dev.meliapp.features.details.data.datasource

import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse

/**
 * Fuente de datos encargada de obtener el detalle de un producto en la API de Mercado Libre.
 */
interface DetailsDataSource {

    /**
     * Obtiene el detalle de un producto en base a su ID.
     * @param itemId Identificador único del producto.
     * @return [ItemDetailResponse] con la información del producto.
     */
    suspend fun getItemDetail(itemId: String): ItemDetailResponse
}