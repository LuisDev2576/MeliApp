package com.luis.dev.meliapp.features.details.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luis.dev.meliapp.ui.theme.PigmentGreen

/**
 * Sección que muestra la información de envío gratuito.
 */
@Composable
fun ShippingInfoSection(stock: Int) {
    Column {
        Row {
            Text(
                text = "Envío gratis ",
                color = PigmentGreen,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "a todo el país"
            )
        }
        Text(
            text = "Conoce los tiempos y las formas de envío",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Row(
            modifier = Modifier
                .clickable {
                    // Manejar el clic aquí
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "Calcular cuándo llega",
                tint = Color.Blue,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = "Calcular cuándo llega",
                color = Color.Blue,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Stock disponible: $stock",
            fontWeight = FontWeight.SemiBold
        )
    }
}