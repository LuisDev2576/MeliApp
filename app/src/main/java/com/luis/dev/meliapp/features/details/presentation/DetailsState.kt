package com.luis.dev.meliapp.features.details.presentation

import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse

/**
 * Representa el estado de la pantalla de resultados y detalle de un producto.
 * @param isLoading Indica si se está cargando la información.
 * @param error Mensaje de error en caso de que ocurra alguna falla.
 * @param itemDetail Información del producto seleccionado.
 */
data class DetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val itemDetail: ItemDetailResponse? = null
)