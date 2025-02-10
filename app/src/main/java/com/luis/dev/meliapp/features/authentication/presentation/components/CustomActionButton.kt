package com.luis.dev.meliapp.features.authentication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.R
import com.luis.dev.meliapp.ui.theme.MeliAppTheme

/**
 * Componente de botón con un diseño personalizable que muestra un texto o un indicador de carga
 * dependiendo de su estado habilitado.
 *
 * @param labelResId ID del recurso string que se mostrará dentro del botón.
 * @param modifier Modificador para personalizar el estilo y el comportamiento del botón.
 * @param isEnabled Indica si el botón está habilitado (true) o deshabilitado (false).
 * @param onAction Acción que se ejecutará cuando se presione el botón.
 */
@Composable
fun CustomActionButton(
    labelResId: Int,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onAction: () -> Unit
) {
    Button(
        enabled = isEnabled,
        onClick = { onAction() },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            disabledContainerColor = Color.Blue.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(10)
    ) {
        if (isEnabled) {
            Text(text = stringResource(id = labelResId), color = Color.White)
        } else {
            CircularProgressIndicator(color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreviewEnabled() {
    MeliAppTheme {
        Box(modifier = Modifier.background(Color.White)) {
            CustomActionButton(labelResId = R.string.create_account, onAction = { /* Handle click */ })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreviewDisabled() {
    MeliAppTheme {
        Box(modifier = Modifier.background(Color.White)) {
            CustomActionButton(labelResId = R.string.loading, isEnabled = false, onAction = { /* Handle click */ })
        }
    }
}