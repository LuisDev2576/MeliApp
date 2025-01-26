package com.luis.dev.meliapp.features.details.data.model

import Installments
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de datos que representa la respuesta del detalle de un producto en la API de Mercado Libre.
 */
@Serializable
data class ItemDetailResponse(
    val id: String,
    @SerialName("site_id") val siteId: String,
    val title: String,
    val price: Double,
    @SerialName("original_price") val originalPrice: Double? = null,
    @SerialName("currency_id") val currencyId: String? = null,
    val condition: String? = null,
    val thumbnail: String? = null,
    val pictures: List<Picture> = emptyList(),
    val attributes: List<Attribute> = emptyList(),
    val installments: Installments? = null,
    val warranty: String? = null,
    val shipping: Shipping? = null // Nuevo campo añadido
) {
    val discountPercentage: Double?
        get() = if (originalPrice != null && originalPrice > 0 && price < originalPrice) {
            ((originalPrice - price) / originalPrice) * 100
        } else {
            null
        }

    /**
     * Propiedad computada para verificar si el envío es gratis.
     */
    val hasFreeShipping: Boolean
        get() = shipping?.freeShipping ?: false
}

/**
 * Representa una imagen dentro de la lista de imágenes (pictures) en la respuesta.
 */
@Serializable
data class Picture(
    val id: String,
    val url: String,
    @SerialName("secure_url") val secureUrl: String? = null,
)

/**
 * Representa un atributo del producto (por ejemplo, color, marca, etc.).
 */
@Serializable
data class Attribute(
    val id: String,
    val name: String,
    @SerialName("value_name") val valueName: String? = null
)

/**
 * Representa la información de envío del producto.
 */
@Serializable
data class Shipping(
    @SerialName("free_shipping") val freeShipping: Boolean? = null,
)
