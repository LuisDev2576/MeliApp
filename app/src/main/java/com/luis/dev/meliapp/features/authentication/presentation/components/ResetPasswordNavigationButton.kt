package com.luis.dev.meliapp.features.authentication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.R

/**
 * Botón de texto interactivo para navegar a la pantalla de recuperación de contraseña.
 *
 * @param onClick Acción que se ejecuta cuando el usuario hace clic en el botón.
 * @param modifier Modificador opcional para personalizar el estilo y el diseño del componente.
 */
@Composable
fun ResetPasswordNavigationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.recover_password),
        modifier = modifier
            .clip(RoundedCornerShape(20))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium
    )
}