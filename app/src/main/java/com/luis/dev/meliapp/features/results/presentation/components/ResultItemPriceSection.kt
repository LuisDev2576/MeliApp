package com.luis.dev.meliapp.features.results.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luis.dev.meliapp.core.utils.ToFormattedPrice
import com.luis.dev.meliapp.ui.theme.PigmentGreen
import kotlin.math.roundToInt

/**
 * Muestra la sección de precios, manejando descuentos si están disponibles.
 *
 * @param price El precio actual del producto.
 * @param originalPrice El precio original del producto antes del descuento.
 * @param discountPercentage El porcentaje de descuento aplicado.
 */
@Composable
fun ResultItemPriceSection(price: Double, originalPrice: Double?, discountPercentage: Double?) {
    Column(verticalArrangement = Arrangement.Center) {
        if (discountPercentage != null && originalPrice != null) {
            Text(
                text = "$ ${originalPrice.toInt().ToFormattedPrice()}",
                textDecoration = TextDecoration.LineThrough,
                color = Color.Gray,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$ ${price.toInt().ToFormattedPrice()}",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${discountPercentage.roundToInt()}% OFF",
                    color = PigmentGreen,
                    fontSize = 12.sp
                )
            }
        } else {
            Text(
                text = "$ ${price.toInt().ToFormattedPrice()}",
                fontSize = 20.sp
            )
        }
    }
}