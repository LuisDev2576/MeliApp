package com.luis.dev.meliapp.features.results.presentation.components

import SearchResultItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Muestra los detalles del producto, incluyendo título, precios, cuotas y envío.
 *
 * @param item El objeto SearchResultItem que contiene la información del producto.
 */
@Composable
fun ResultItemProductDetails(item: SearchResultItem) {
    Column {
        ResultItemProductTitle(title = item.title)
        Spacer(modifier = Modifier.height(4.dp))
        ResultItemPriceSection(
            price = item.price,
            originalPrice = item.originalPrice,
            discountPercentage = item.discountPercentage
        )
        Spacer(modifier = Modifier.height(4.dp))
        ResultItemInstallmentsSection(installments = item.installments)
        ResultItemShippingSection(shipping = item.shipping)
    }
}