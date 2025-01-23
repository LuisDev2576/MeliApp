package com.luis.dev.meliapp.features.result_list.presentation.components

import Attribute
import Installments
import SearchResultItem
import Shipping
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.luis.dev.meliapp.core.utils.toFormattedPrice
import com.luis.dev.meliapp.R
import com.luis.dev.meliapp.core.utils.toHttps
import com.luis.dev.meliapp.ui.theme.Viridian
import kotlin.math.roundToInt


/**
 * Componente principal que representa un ítem de resultado de búsqueda.
 *
 * @param item El objeto SearchResultItem que contiene la información del producto.
 */
@Composable
fun ResultItem(item: SearchResultItem) {
    Column {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .clickable {
                    // TODO: Implementar la acción al hacer clic
                }
                .padding(8.dp)
        ) {
            ProductThumbnail(thumbnailUrl = item.thumbnail, title = item.title)
            Spacer(modifier = Modifier.width(12.dp))
            ProductDetails(item = item)
        }
        MyHorizontalDivider()
    }
}

/**
 * Muestra la imagen del producto.
 *
 * @param thumbnailUrl La URL de la imagen del producto.
 * @param title La descripción del contenido de la imagen para accesibilidad.
 */
@Composable
fun ProductThumbnail(thumbnailUrl: String, title: String) {
    val secureThumbnailUrl = thumbnailUrl.toHttps()
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(secureThumbnailUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.no_image_available),
        contentDescription = title,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

/**
 * Muestra los detalles del producto, incluyendo título, precios, cuotas y envío.
 *
 * @param item El objeto SearchResultItem que contiene la información del producto.
 */
@Composable
fun ProductDetails(item: SearchResultItem) {
    Column {
        ProductTitle(title = item.title)
        Spacer(modifier = Modifier.height(4.dp))
        PriceSection(
            price = item.price,
            originalPrice = item.originalPrice,
            discountPercentage = item.discountPercentage
        )
        Spacer(modifier = Modifier.height(4.dp))
        InstallmentsSection(installments = item.installments)
        ShippingSection(shipping = item.shipping)
    }
}

/**
 * Muestra el título del producto.
 *
 * @param title El título del producto.
 */
@Composable
fun ProductTitle(title: String) {
    Text(
        text = title,
        color = Color.Black,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        fontSize = 14.sp
    )
}

/**
 * Muestra la sección de precios, manejando descuentos si están disponibles.
 *
 * @param price El precio actual del producto.
 * @param originalPrice El precio original del producto antes del descuento.
 * @param discountPercentage El porcentaje de descuento aplicado.
 */
@Composable
fun PriceSection(price: Double, originalPrice: Double?, discountPercentage: Double?) {
    Column(verticalArrangement = Arrangement.Center) {
        if (discountPercentage != null && originalPrice != null) {
            Text(
                text = "$ ${originalPrice.toInt().toFormattedPrice()}",
                textDecoration = TextDecoration.LineThrough,
                color = Color.Gray,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$ ${price.toInt().toFormattedPrice()}",
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${discountPercentage.roundToInt()}% OFF",
                    color = Viridian,
                    fontSize = 12.sp
                )
            }
        } else {
            Text(
                text = "$ ${price.toInt().toFormattedPrice()}",
                fontSize = 20.sp,
            )
        }
    }
}

/**
 * Muestra la información de las cuotas disponibles para el producto.
 *
 * @param installments El objeto Installments que contiene la información de las cuotas.
 */
@Composable
fun InstallmentsSection(installments: Installments?) {
    installments?.let {
        if (it.quantity > 0) {
            Row {
                Text(
                    text = "en",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = " ${it.quantity} cuotas de $ ${it.amount.toInt().toFormattedPrice()}${if (it.noInterest) " sin interés" else ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Viridian,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Muestra la información de envío del producto, indicando si es gratis.
 *
 * @param shipping El objeto Shipping que contiene la información de envío.
 */
@Composable
fun ShippingSection(shipping: Shipping?) {
    shipping?.let {
        if (it.freeShipping) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Envío gratis",
                style = MaterialTheme.typography.bodySmall.copy(color = Viridian),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Dibuja una línea horizontal divisoria.
 *
 * @param color El color de la línea divisoria.
 */
@Composable
fun MyHorizontalDivider(color: Color = Color.LightGray.copy(alpha = 0.5f)) {
    HorizontalDivider(
        color = color,
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth()
    )
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
    ResultItem(item = item)
}