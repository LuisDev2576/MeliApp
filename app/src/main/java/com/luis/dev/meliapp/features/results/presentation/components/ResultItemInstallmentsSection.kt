package com.luis.dev.meliapp.features.results.presentation.components

import Installments
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.luis.dev.meliapp.core.utils.ToFormattedPrice
import com.luis.dev.meliapp.ui.theme.PigmentGreen

/**
 * Muestra la información de las cuotas disponibles para el producto.
 *
 * @param installments El objeto Installments que contiene la información de las cuotas.
 */
@Composable
fun ResultItemInstallmentsSection(installments: Installments?) {
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
                    text = " ${it.quantity} cuotas de $ ${it.amount.toInt().ToFormattedPrice()}${if (it.noInterest) " sin interés" else ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = PigmentGreen,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}