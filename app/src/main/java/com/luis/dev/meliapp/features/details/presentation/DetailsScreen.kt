package com.luis.dev.meliapp.features.details.presentation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luis.dev.meliapp.core.utils.toFormattedPrice
import com.luis.dev.meliapp.features.details.presentation.components.ImageCarousel
import com.luis.dev.meliapp.features.details.presentation.components.Installments
import com.luis.dev.meliapp.features.details.data.model.Attribute
import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse
import com.luis.dev.meliapp.features.details.data.model.Picture
import com.luis.dev.meliapp.features.results.presentation.components.ErrorView
import com.luis.dev.meliapp.features.results.presentation.components.LoadingIndicator
import com.luis.dev.meliapp.features.results.presentation.components.NoResultsView
import com.luis.dev.meliapp.ui.theme.MeliAppTheme
import com.luis.dev.meliapp.ui.theme.PigmentGreen
import kotlin.math.roundToInt

/**
 * Pantalla que muestra los detalles de un producto seleccionado.
 *
 */
@Composable
fun DetailsScreen(
    detailsState: DetailsState,
) {

    when {
        detailsState.isLoading -> {
            LoadingIndicator()
        }
        detailsState.error != null -> {
            ErrorView(errorMessage = detailsState.error)
        }
        detailsState.itemDetail != null -> {
            ItemDetailContent(itemDetail = detailsState.itemDetail)
        }
        else -> {
            NoResultsView()
        }
    }
}

/**
 * Muestra el contenido detallado del producto.
 *
 * @param itemDetail Información detallada del producto.
 */
@Composable
fun ItemDetailContent(itemDetail: ItemDetailResponse) {

    Log.d("ItemPrice", "ItemId ${itemDetail.id} / ItemPrice ${itemDetail.price} / ItemOriginalPrice ${itemDetail.originalPrice}")
    val cantidadVendidos by rememberSaveable { mutableIntStateOf((2..1000).random()) }
    val cantidadDeResenas by rememberSaveable { mutableIntStateOf((1..cantidadVendidos).random()) }

    val calificacion by rememberSaveable { mutableIntStateOf((1..5).random()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if(itemDetail.condition != null){
                Text(
                    text = "${itemDetail.condition} | + $cantidadVendidos vendidos",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }else{
                Text(
                    text = "+ $cantidadVendidos vendidos",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${calificacion.toFloat()}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                repeat(calificacion) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Estrellas",
                        tint = Color.Blue,
                        modifier = Modifier.size(15.dp)
                    )
                }
                repeat(5 - calificacion) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Estrellas",
                        modifier = Modifier.size(15.dp),
                        tint = Color.LightGray
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "($cantidadDeResenas)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = itemDetail.title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(16.dp))
        ImageCarousel(
            imageList = itemDetail.pictures.map { it.secureUrl!! }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.Center) {
            if (itemDetail.discountPercentage != null && itemDetail.originalPrice != null) {
                Text(
                    text = "$ ${itemDetail.originalPrice.toInt().toFormattedPrice()}",
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$ ${itemDetail.price.toInt().toFormattedPrice()}",
                        fontSize = 30.sp,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${itemDetail.discountPercentage!!.roundToInt()}% OFF",
                        color = PigmentGreen,
                        fontSize = 16.sp
                    )
                }
            } else {
                Text(
                    text = "$ ${itemDetail.price.toInt().toFormattedPrice()}",
                    fontSize = 30.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Installments(price = itemDetail.price)

        // Atributos del producto
        itemDetail.attributes.take(5).forEach { attribute ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "${attribute.name}: ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = attribute.valueName ?: "N/A",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Garantía
        itemDetail.warranty?.let { warranty ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Garantía: ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = warranty,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun DetailScreenLoadingPreview() {
    val dummyState = DetailsState(isLoading = true)
    MeliAppTheme {
        DetailScreenPreview(dummyState)
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun DetailScreenErrorPreview() {
    val dummyState = DetailsState(error = "No se pudo cargar el detalle del producto.")
    MeliAppTheme {
        DetailScreenPreview(dummyState)
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun DetailScreenSuccessPreview() {
    val dummyItem = ItemDetailResponse(
        id = "MLA1461972881",
        siteId = "MLA",
        title = "Apple iPhone 15 (128 Gb) - Negro",
        price = 1899995.0,
        currencyId = "ARS",
        thumbnail = "http://http2.mlstatic.com/D_779617-MLA71782867320_092023-I.jpg",
        pictures = listOf(
            Picture(id = "779617-MLA71782867320_092023", url = "https://http2.mlstatic.com/D_779617-MLA71782867320_092023-O.jpg")
        ),
        attributes = listOf(
            Attribute(id = "BRAND", name = "Marca", valueName = "Apple"),
            Attribute(id = "COLOR", name = "Color", valueName = "Negro"),
            Attribute(id = "GTIN", name = "Código universal de producto", valueName = "195949035937"),
            Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
            Attribute(id = "LINE", name = "Línea", valueName = "iPhone 15")
        ),
        warranty = "Garantía de fábrica: 12 meses"
    )

    val dummyState = DetailsState(
        isLoading = false,
        error = null,
        itemDetail = dummyItem
    )

    MeliAppTheme {
        DetailScreenPreview(dummyState)
    }
}

@Composable
fun DetailScreenPreview(uiState: DetailsState) {
    when {
        uiState.isLoading -> {
            LoadingIndicator()
        }
        uiState.error != null -> {
            ErrorView(errorMessage = uiState.error)
        }
        uiState.itemDetail != null -> {
            ItemDetailContent(itemDetail = uiState.itemDetail)
        }
        else -> {
            NoResultsView()
        }
    }
}
