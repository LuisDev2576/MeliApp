package com.luis.dev.meliapp.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.luis.dev.meliapp.R
import com.luis.dev.meliapp.ui.theme.PigmentGreen

/**
 * Componente que muestra una lista horizontal de categorías con sus imágenes y nombres.
 * Incluye una tarjeta superior indicando la disponibilidad de envíos gratis.
 *
 * @param itemList Lista de pares donde el primer elemento es el nombre de la categoría y el segundo es la URL de la imagen.
 */
@Composable
fun CategoriesSection(
    itemList: List<Pair<String, String>> // Nombre y URL de la imagen
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.LocalShipping,
                contentDescription = "Envío gratis",
                tint = PigmentGreen
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "Envío gratis",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PigmentGreen
            )
            Text(
                " en millones de productos desde $60.000.",
                fontSize = 12.sp
            )
        }
    }
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
            .height(100.dp)
    ) {
        items(itemList) {
            CategoryItem(
                title = it.first,
                imageUrl = it.second
            )
        }
    }
}

/**
 * Elemento individual de categoría que muestra una imagen dentro de un círculo y su nombre debajo.
 *
 * @param imageUrl URL de la imagen que representa la categoría.
 * @param title Nombre de la categoría.
 */
@Composable
fun CategoryItem(
    imageUrl: String,
    title: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .width(80.dp)
    ) {
        Card(
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .size(70.dp),
            onClick = { }
        ) {
            AsyncImage(
                model = imageUrl,
                placeholder = painterResource(R.drawable.no_image_available),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(6.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 9.sp,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp
        )
    }
}