package com.example.weatherkotlin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Preview(showBackground = true)
@Composable
fun MainPage() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(5.dp),

        ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Green.copy(alpha = 0.5f),
            ),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "19.08.2003", modifier = Modifier
                        .padding(16.dp), fontSize = 14.sp
                )
                AsyncImage(
                    model = "https:/api.openweathermap.org/img/w/04n",
                    contentDescription = "04n",
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = "City", modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 20.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "20",
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "C",
                    modifier = Modifier
                        .padding(horizontal = 8.dp),

                    textAlign = TextAlign.Center,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}