package com.luis.dev.meliapp.features.details.data.repository

import com.luis.dev.meliapp.features.details.data.datasource.DetailsDataSource
import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse

/**
 * Implementación de [DetailsRepository].
 * @property dataSource Fuente de datos para obtener el detalle del producto.
 */
class DetailsRepositoryImpl(
    private val dataSource: DetailsDataSource
) : DetailsRepository {

    /**
     * Obtiene el detalle de un producto usando la fuente de datos.
     * @param itemId Identificador único del producto.
     * @return [ItemDetailResponse] con la información del producto.
     */
    override suspend fun getItemDetail(itemId: String): ItemDetailResponse {
        return dataSource.getItemDetail(itemId)
    }
}
