package com.luis.dev.meliapp.features.results.presentation.components

import Shipping
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.ui.theme.PigmentGreen

/**
 * Muestra la información de envío del producto, indicando si es gratis.
 *
 * @param shipping El objeto Shipping que contiene la información de envío.
 */
@Composable
fun ResultItemShippingSection(shipping: Shipping?) {
    shipping?.let {
        if (it.freeShipping) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Envío gratis",
                style = MaterialTheme.typography.bodySmall.copy(color = PigmentGreen),
                fontWeight = FontWeight.Bold
            )
        }
    }
}