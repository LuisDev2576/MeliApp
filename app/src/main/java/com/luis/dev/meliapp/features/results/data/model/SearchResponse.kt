import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("site_id") val siteId: String,
    @SerialName("query") val query: String? = null,
    val paging: Paging,
    val results: List<SearchResultItem>
)

@Serializable
data class Paging(
    val total: Int,
    val offset: Int,
    val limit: Int,
    @SerialName("primary_results") val primaryResults: Int
)

/**
 * Representa un ítem individual de la búsqueda.
 */
@Serializable
data class SearchResultItem(
    val id: String,
    val title: String,

    /**
     * Precio final (con descuento aplicado, si lo hay).
     */
    val price: Double,

    val thumbnail: String,
    val permalink: String,

    /**
     * Nombre de la tienda oficial (si existe).
     */
    @SerialName("official_store_name")
    val officialStoreName: String? = null,

    /**
     * Precio original (antes del descuento).
     */
    @SerialName("original_price")
    val originalPrice: Double? = null,

    /**
     * Indica si el envío es gratis.
     * Esto está dentro de "shipping.free_shipping" en tu JSON,
     * así que podemos parsearlo con una clase anidada o en un custom mapper.
     */
    val shipping: Shipping? = null,

    /**
     * Información de cuotas.
     */
    val installments: Installments? = null,

    /**
     * Lista de atributos; de aquí se puede extraer la marca (BRAND), color, etc.
     */
    val attributes: List<Attribute>? = null
) {
    /**
     * Puedes calcular el porcentaje de descuento si originalPrice y price no son nulos,
     * o dejarlo en null si la API no proporciona una base para calcularlo.
     */
    val discountPercentage: Double?
        get() = if (originalPrice != null && originalPrice > 0 && price < originalPrice) {
            ((originalPrice - price) / originalPrice) * 100
        } else {
            null
        }

    /**
     * Puedes obtener la marca recorriendo `attributes` para encontrar "BRAND".
     */
    val brand: String?
        get() = attributes
            ?.firstOrNull { it.id == "BRAND" }
            ?.valueName
}

@Serializable
data class Shipping(
    @SerialName("free_shipping") val freeShipping: Boolean
)

/**
 * Modelo para describir las cuotas.
 * Si `rate` es 0, quiere decir que son cuotas sin interés.
 */
@Serializable
data class Installments(
    val quantity: Int,
    val amount: Double,
    val rate: Double,
    @SerialName("currency_id") val currencyId: String
) {
    val noInterest: Boolean
        get() = rate == 0.0
}

/**
 * Ejemplo de clase para atributos adicionales del producto (e.g., BRAND).
 */
@Serializable
data class Attribute(
    val id: String,
    val name: String,
    @SerialName("value_name")
    val valueName: String?
)