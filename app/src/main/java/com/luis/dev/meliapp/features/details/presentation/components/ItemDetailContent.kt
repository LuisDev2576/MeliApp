package com.luis.dev.meliapp.features.details.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse
import com.luis.dev.meliapp.features.results.presentation.components.ResultItemPriceSection

/**
 * Muestra el contenido detallado del producto.
 *
 * @param itemDetail Información detallada del producto.
 */
@Composable
fun ItemDetailContent(itemDetail: ItemDetailResponse) {

    val cantidadVendidos by rememberSaveable { mutableIntStateOf((2..1000).random()) }
    val cantidadDeResenas by rememberSaveable { mutableIntStateOf((1..cantidadVendidos).random()) }
    val calificacion by rememberSaveable { mutableIntStateOf((1..5).random()) }
    val stock by rememberSaveable { mutableIntStateOf((1..150).random()) }
    val randonNumberQuantity by rememberSaveable { mutableIntStateOf((3..12).random()) }
    val amount = itemDetail.price / randonNumberQuantity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        HeaderSection(
            condition = itemDetail.condition,
            cantidadVendidos = cantidadVendidos,
            calificacion = calificacion,
            cantidadDeResenas = cantidadDeResenas
        )
        Spacer(modifier = Modifier.height(8.dp))
        TitleSection(title = itemDetail.title)
        Spacer(modifier = Modifier.height(16.dp))
        ImageCarousel(
            imageList = itemDetail.pictures.map { it.secureUrl!! }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ResultItemPriceSection(
            price = itemDetail.price,
            originalPrice = itemDetail.originalPrice,
            discountPercentage = itemDetail.discountPercentage
        )
        Spacer(modifier = Modifier.height(16.dp))
        InstallmentsSection(numeroDeCuotas = randonNumberQuantity, amount = amount)
        if(itemDetail.hasFreeShipping){
            Spacer(modifier = Modifier.height(16.dp))
            ShippingInfoSection(stock = stock)
        }
        Spacer(modifier = Modifier.height(16.dp))
        BuyOrSaveSection(
            onBuyButtonClick = { /* Acción al hacer clic en "Comprar" */ },
            onSaveButtonClick = { /* Acción al hacer clic en "Guardar" */ }
        )
        itemDetail.warranty?.let { warranty ->
            Spacer(modifier = Modifier.height(16.dp))
            WarrantySection(warranty = warranty)
        }
    }
}
