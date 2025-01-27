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
                "https://github-production-user-asset-6210df.s3.amazonaws.com/123314822/406834073-ad8e77a1-2bb2-43c4-a5dd-b5da22c9197c.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250127%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250127T074620Z&X-Amz-Expires=300&X-Amz-Signature=9ca4fb26be83400740fe28627c5401aea03a4f1892e9d0d05efbea705e9ee4a5&X-Amz-SignedHeaders=host",
                "https://github-production-user-asset-6210df.s3.amazonaws.com/123314822/406834615-9b889250-482b-439b-8f54-90565443d0c5.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250127%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250127T074555Z&X-Amz-Expires=300&X-Amz-Signature=cf4cc3a934f9c43b7a2d84e4559493f3b49295974861eb519d02875be8c4abdb&X-Amz-SignedHeaders=host",
                "https://github-production-user-asset-6210df.s3.amazonaws.com/123314822/406835153-de129552-c756-43d3-bf9f-0762a0893f57.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250127%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250127T074706Z&X-Amz-Expires=300&X-Amz-Signature=714542a1e86d9a185ff4085634cf25b4fdb6c35b91448edd1e21b32fce55ad18&X-Amz-SignedHeaders=host",
                "https://github-production-user-asset-6210df.s3.amazonaws.com/123314822/406835282-648935eb-5f97-4fae-8a3a-44bcb48299ed.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250127%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250127T074742Z&X-Amz-Expires=300&X-Amz-Signature=cfd610ffa31952e0b41b351b3dbc2ead1bba256a1a768f9b88db00fbec2c2de2&X-Amz-SignedHeaders=host",
                "https://github-production-user-asset-6210df.s3.amazonaws.com/123314822/406835381-bf1a89a1-61e5-49bc-9ac5-24c38058da17.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250127%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250127T074809Z&X-Amz-Expires=300&X-Amz-Signature=7efb895b2c0c89b062650ca211a8c7f82fe553084be1780bd12528561245564f&X-Amz-SignedHeaders=host",
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
            imageUrl = "https://github-production-user-asset-6210df.s3.amazonaws.com/123314822/406836167-7824a066-044a-4af8-a3e9-ffe14f38d04b.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250127%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250127T075141Z&X-Amz-Expires=300&X-Amz-Signature=8225d4c8f5e4b3e92169d1b9d4dbb1e77f6341bdc7a52c4277d1909aa0ae0ae3&X-Amz-SignedHeaders=host"
        )
    }

    BackHandler {
        onBack()
    }
}



