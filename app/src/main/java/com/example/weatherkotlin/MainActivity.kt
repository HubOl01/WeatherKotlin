package com.example.weatherkotlin

import android.app.DownloadManager.Request
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherkotlin.screens.MainPage
import com.example.weatherkotlin.screens.TabLayout
import com.example.weatherkotlin.ui.theme.WeatherKotlinTheme
import kotlinx.serialization.json.Json
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = Color.Blue.copy(alpha = 0.5f)
                ) {
                    Column {
                        MainPage()
                        TabLayout()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, context: Context) {
    val lat: Double = 56.99719;
    val lon: Double = 40.97139;
    val state = remember {
        mutableStateOf("null")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Current $nameCity = ${state.value}",
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Button(onClick = {
                getWeather(lat, lon, state, context)
            }) {
                Text(text = "Нажми сюда")
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    WeatherKotlinTheme {
//        Greeting("Android")
//    }
//}
var nameCity: String = "null";
private fun getWeather(lat: Double, lon: Double, state: MutableState<String>, context: Context) {
    val url = "https://api.openweathermap.org/data/2.5/forecast?" +
            "lat=${lat}&" +
            "lon=${lon}&" +
            "lang=ru&" +
            "appid=e847b8dad5ccbf86bebe66ecdb5fdf24"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(com.android.volley.Request.Method.GET, url,
        { response ->
            val obj = Json.decodeFromString<WeatherModel>(response)
            nameCity = obj.city.name.toString()
            state.value = obj.list.first().weather.first().description
//            val obj = JSONObject(response)
//            state.value = obj.getJSONObject("city").getString("name")
        },
        { error ->

        })
    queue.add(stringRequest)
}