package com.luis.dev.meliapp.features.home.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.luis.dev.meliapp.features.home.presentation.components.AdvertisementBanner
import com.luis.dev.meliapp.features.home.presentation.components.AdvertisementCarousel
import com.luis.dev.meliapp.features.home.presentation.components.CategoriesSection

/**
 * Pantalla principal de la aplicación que muestra un carrusel publicitario, una sección de categorías
 * y un banner publicitario adicional. También permite manejar la acción de retroceso.
 *
 * @param onBack Callback que se ejecuta cuando el usuario presiona el botón de retroceso.
 */
@Composable
fun HomeScreen(
    onBack: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AdvertisementCarousel(
            imageList = listOf(
                "https://i.postimg.cc/pdDfhHzJ/Captura-de-pantalla-de-2025-01-27-02-17-26.png",
                "https://i.postimg.cc/43q3g7m8/Captura-de-pantalla-de-2025-01-27-02-17-11.png",
                "https://i.postimg.cc/h4H3dS7H/Captura-de-pantalla-de-2025-01-27-02-16-59.png",
                "https://i.postimg.cc/GpWsjSJh/Captura-de-pantalla-de-2025-01-27-02-16-42.png",
                "https://i.postimg.cc/kgV4hv5V/Captura-de-pantalla-de-2025-01-27-02-16-16.png",
             )
        )
        CategoriesSection(
            itemList = listOf(
                Pair("Gatos", "https://http2.mlstatic.com/storage/categories-api/images/44e16f0c-e587-4036-8e9f-10a9ed4a559f.png"),
                Pair("Perros", "https://http2.mlstatic.com/storage/categories-api/images/d290aba2-b6bf-4274-bac8-0284b1cde8fa.png"),
                Pair("Agro", "https://http2.mlstatic.com/storage/categories-api/images/311380fe-3734-4322-bb71-c956fcfe627d.png"),
                Pair("Motos", "https://http2.mlstatic.com/storage/categories-api/images/e1a43666-ad57-4b8b-b405-f9d04dbbd8fc.png"),
                Pair("Televisores", "https://http2.mlstatic.com/storage/categories-api/images/9d4d6144-21e3-4d1b-91c9-f47d18e11113.png"),
                Pair("Accesorios para vehiculos", "https://http2.mlstatic.com/storage/categories-api/images/6fc20d84-2ce6-44ee-8e7e-e5479a78eab0.png"),
                Pair("Celulares", "https://http2.mlstatic.com/storage/categories-api/images/fdca1620-3b63-4af2-bc0b-aeed17048d5d.png"),
                Pair("Computación", "https://http2.mlstatic.com/storage/categories-api/images/f96f9ecc-dfe6-4cf9-a270-4c0cee23f868.png"),
                Pair("Hogar", "https://http2.mlstatic.com/storage/categories-api/images/5194ee98-9095-4ef6-b9a5-c78073fa60af.png"),
            )
        )
        AdvertisementBanner(
            imageUrl = "https://i.postimg.cc/RCsv5Jyw/Captura-de-pantalla-de-2025-01-27-02-17-52.png"
        )
    }

    BackHandler {
        onBack()
    }
}



