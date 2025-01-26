package com.luis.dev.meliapp.features.details.presentation.components

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
import com.luis.dev.meliapp.core.utils.toFormattedPrice
import com.luis.dev.meliapp.ui.theme.PigmentGreen
import kotlin.math.roundToInt

/**
 * Sección que muestra el precio, descuento y precio original si aplica.
 */
@Composable
fun PriceSection(
    price: Double,
    originalPrice: Double?,
    discountPercentage: Double?
) {
    Column(verticalArrangement = Arrangement.Center) {
        if (discountPercentage != null && originalPrice != null) {
            Text(
                text = "$ ${originalPrice.toInt().toFormattedPrice()}",
                textDecoration = TextDecoration.LineThrough,
                color = Color.Gray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$ ${price.toInt().toFormattedPrice()}",
                    fontSize = 30.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${discountPercentage.roundToInt()}% OFF",
                    color = PigmentGreen,
                    fontSize = 16.sp
                )
            }
        } else {
            Text(
                text = "$ ${price.toInt().toFormattedPrice()}",
                fontSize = 30.sp,
            )
        }
    }
}