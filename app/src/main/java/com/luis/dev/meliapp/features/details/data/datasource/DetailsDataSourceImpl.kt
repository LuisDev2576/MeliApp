package com.luis.dev.meliapp.features.details.data.datasource

import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Implementación de [DetailsDataSource] que hace la llamada a la API de Mercado Libre.
 * @property httpClient Cliente HTTP para realizar las solicitudes.
 * @property jsonFormatter Formateador JSON para parsear las respuestas.
 */
class DetailsDataSourceImpl(
    private val httpClient: HttpClient
) : DetailsDataSource {

    /**
     * Obtiene el detalle de un producto desde la URL https://api.mercadolibre.com/items/$ITEM_ID.
     * @param itemId Identificador único del producto.
     * @return [ItemDetailResponse] con la información del producto.
     */
    override suspend fun getItemDetail(itemId: String): ItemDetailResponse {
        val response = httpClient.get("https://api.mercadolibre.com/items/$itemId")
        return response.body()
    }
}