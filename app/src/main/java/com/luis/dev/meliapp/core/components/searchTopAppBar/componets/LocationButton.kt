package com.luis.dev.meliapp.core.components.searchTopAppBar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luis.dev.meliapp.ui.theme.MeliAppTheme

/**
 * Botón para indicar y seleccionar una ubicación.
 *
 * @param onClick Acción que se ejecuta al hacer clic en el botón.
 */
@Composable
fun LocationButton(
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 4.dp, top = 8.dp, bottom = 8.dp)
            .clip(shape = CircleShape)
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(16.dp)
        )
        Text(
            "Ingresa tu ubicación",
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationButtonPreview() {
    MeliAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
            LocationButton(onClick = {})
        }
    }
}
