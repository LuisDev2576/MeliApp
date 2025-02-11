package com.luis.dev.meliapp.features.authentication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

/**
 * Componente para mostrar un saludo con un título y, opcionalmente, instrucciones adicionales.
 *
 * @param titleResId ID del recurso de texto que representa el título del saludo.
 * @param instructionsResId ID opcional del recurso de texto que representa las instrucciones adicionales.
 * Si no se proporciona, solo se mostrará el título.
 */
@Composable
fun GreetingMessage(
    titleResId: Int,
    instructionsResId: Int? = null
) {
    Column(
        modifier = Modifier
            .widthIn(max = 600.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(id = titleResId),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.padding(10.dp))
        instructionsResId?.let {
            Text(
                text = stringResource(id = it),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}