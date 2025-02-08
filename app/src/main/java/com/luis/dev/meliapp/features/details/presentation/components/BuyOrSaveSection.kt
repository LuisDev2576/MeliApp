package com.luis.dev.meliapp.features.details.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
/**
 * Sección que contiene dos botones: uno para realizar la compra inmediata y otro para agregar el artículo al carrito.
 *
 * @param onBuyButtonClick Callback que se ejecuta cuando el usuario presiona el botón "Comprar ahora".
 * @param onSaveButtonClick Callback que se ejecuta cuando el usuario presiona el botón "Agregar al carrito".
 */
@Composable
fun BuyOrSaveSection(
    onBuyButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit
) {
    Button(
        onClick = onBuyButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Comprar ahora")
    }
    Button(
        onClick = onSaveButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue.copy(alpha = 0.12f),
            contentColor = Color.Blue
        ),
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Agregar al carrito")
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun BuyOrSaveSectionPreview() {
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        BuyOrSaveSection(
            onBuyButtonClick = { /*TODO*/ },
            onSaveButtonClick = { /*TODO*/ }
        )
    }
}