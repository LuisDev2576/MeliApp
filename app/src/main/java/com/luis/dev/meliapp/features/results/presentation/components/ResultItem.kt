package com.luis.dev.meliapp.features.results.presentation.components

import Attribute
import Installments
import SearchResultItem
import Shipping
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 * Componente principal que representa un ítem de resultado de búsqueda.
 *
 * @param item El objeto SearchResultItem que contiene la información del producto.
 */
@Composable
fun ResultItem(item: SearchResultItem, onClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .clickable {
                   onClick()
                }
                .padding(8.dp)
        ) {
            ResultItemProductThumbnail(thumbnailUrl = item.thumbnail, title = item.title)
            Spacer(modifier = Modifier.width(12.dp))
            ResultItemProductDetails(item = item)
        }
        ResultItemHorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
fun ResultItemPreview() {
    val item = SearchResultItem(
        id = "MLA1970592170",
        title = "Apple iPhone 16 (128 Gb) - Blanco - Distribuidor Autorizado",
        price = 3387779.0,
        thumbnail = "http://http2.mlstatic.com/D_943288-MLU78891932006_092024-I.jpg",
        permalink = "https://www.mercadolibre.com.ar/apple-iphone-16-128-gb-blanco-distribuidor-autorizado/p/MLA1040287791#wid=MLA1970592170&sid=unknown",
        officialStoreName = "Apple",
        originalPrice = 4399999.0,
        shipping = Shipping(
            freeShipping = true
        ),
        installments = Installments(
            quantity = 12,
            amount = 282314.92,
            rate = 0.0,
            currencyId = "ARS"
        ),
        attributes = listOf(
            Attribute(id = "BRAND", name = "Marca", valueName = "Apple"),
            Attribute(id = "COLOR", name = "Color", valueName = "Blanco"),
            Attribute(id = "GTIN", name = "Código universal de producto", valueName = "195949821998"),
            Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
            Attribute(id = "LINE", name = "Línea", valueName = "iPhone 16"),
            Attribute(id = "MAIN_COLOR", name = "Color principal", valueName = "Blanco"),
            Attribute(id = "MODEL", name = "Modelo", valueName = "iPhone 16"),
            Attribute(id = "PROCESSOR_MODEL", name = "Modelo del procesador", valueName = "A18"),
            Attribute(id = "WEIGHT", name = "Peso", valueName = "170 g")
        )
    )
    ResultItem(item = item){

    }
}