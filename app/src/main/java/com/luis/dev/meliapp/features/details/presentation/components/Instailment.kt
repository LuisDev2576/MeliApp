package com.luis.dev.meliapp.features.details.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luis.dev.meliapp.core.utils.toFormattedPrice
import com.luis.dev.meliapp.ui.theme.PigmentGreen

@Composable
fun Installments(price: Double){
    val randonNumberQuantity = (3..12).random()
    val amount = price / randonNumberQuantity
    Column {
        Row {
            Text(
                text = "en",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
            Text(
                text = " $randonNumberQuantity cuotas de $ ${amount.toInt().toFormattedPrice()} sin interés",
                style = MaterialTheme.typography.bodySmall,
                color = PigmentGreen,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp
            )
        }
        Text(
            text = "Ver los medios de pago",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Blue,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable{

                }
        )
    }
}