package com.example.weatherkotlin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherkotlin.data.Weather
import java.time.format.DateTimeFormatter

//@Preview(showBackground = true)
@Composable
fun ListItem(item: Weather) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.Green.copy(alpha = 0.5f),
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.dataTime.format(
                        DateTimeFormatter.ofPattern("HH:mm")
                    )
                )
                Text(text = item.description, color = Color.White)
            }
            Column {
                Text(text = "${item.currentTemp}℃", color = Color.White, fontSize = 20.sp)
                Text(text = "${item.maxTemp}/${item.minTemp}", color = Color.White)
            }
            AsyncImage(
                model = "https:/api.openweathermap.org/img/w/${item.icon}",
                contentDescription = "04n",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}
