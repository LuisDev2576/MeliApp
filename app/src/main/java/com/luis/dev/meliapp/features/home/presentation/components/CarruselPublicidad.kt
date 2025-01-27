package com.luis.dev.meliapp.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.*
import com.luis.dev.meliapp.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarruselPublicidad(
    imageList: List<String>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        // Establece la página inicial en el centro para permitir desplazamiento infinito
        initialPage = Int.MAX_VALUE / 2
    )

    // Obtener el índice de la imagen actual
    val currentPage = pagerState.currentPage
    val actualPage = currentPage % imageList.size

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        Color.White
                    )
                )
            )// Ajusta el padding según tus necesidades
    ) {
        HorizontalPager(
            count = Int.MAX_VALUE,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Ajusta la altura según tus necesidades
        ) { page ->
            // Calcula la posición real de la imagen
            val index = page % imageList.size
            CarouselItem(imageUrl = imageList[index])
        }
    }

    // Opcional: Animación automática para cambiar de página
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(5000) // Cambia cada 3 segundos
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }
}

@Composable
private fun CarouselItem(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        placeholder = painterResource(R.drawable.no_image_available),
        contentDescription = imageUrl,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .height(170.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clickable{

            }
    )
}
