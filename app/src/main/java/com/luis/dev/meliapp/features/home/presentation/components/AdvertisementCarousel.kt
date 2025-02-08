package com.luis.dev.meliapp.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.luis.dev.meliapp.R.drawable.no_image_available
import kotlinx.coroutines.launch

/**
 * Carrusel de publicidad que muestra imágenes en un desplazamiento horizontal infinito,
 * con un efecto de cambio automático de página cada 5 segundos.
 *
 * @param imageList Lista de URLs de las imágenes que se mostrarán en el carrusel.
 * @param modifier Modificador opcional para personalizar el diseño del carrusel.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun AdvertisementCarousel(
    imageList: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2
    )

    val coroutineScope = rememberCoroutineScope()

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
            )
    ) {
        HorizontalPager(
            count = Int.MAX_VALUE,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            val index = page % imageList.size
            CarouselItem(imageUrl = imageList[index])
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(5000)
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }
}

/**
 * Elemento individual dentro del carrusel que muestra una imagen publicitaria.
 *
 * @param imageUrl URL de la imagen que se mostrará en el elemento del carrusel.
 */
@Composable
private fun CarouselItem(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        placeholder = painterResource(no_image_available),
        contentDescription = imageUrl,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .height(170.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clickable { }
    )
}