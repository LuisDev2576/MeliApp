package com.luis.dev.meliapp.features.details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Sección de encabezado que muestra la condición, cantidad vendidos y calificación.
 */
@Composable
fun HeaderSection(
    condition: String?,
    cantidadVendidos: Int,
    calificacion: Int,
    cantidadDeResenas: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (condition != null) {
            Text(
                text = "$condition | + $cantidadVendidos vendidos",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        } else {
            Text(
                text = "+ $cantidadVendidos vendidos",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }

        RatingSection(
            calificacion = calificacion,
            cantidadDeResenas = cantidadDeResenas
        )
    }
}

/**
 * Sección que muestra la calificación con estrellas y cantidad de reseñas.
 */
@Composable
private fun RatingSection(
    calificacion: Int,
    cantidadDeResenas: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$calificacion",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(4.dp))
        repeat(calificacion) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Estrella",
                tint = Color.Blue,
                modifier = Modifier.size(15.dp)
            )
        }
        repeat(5 - calificacion) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Estrella vacía",
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