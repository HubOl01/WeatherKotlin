package com.example.weatherkotlin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherkotlin.data.Weather
import com.example.weatherkotlin.screens.MainPage
import com.example.weatherkotlin.screens.TabLayout
import com.example.weatherkotlin.ui.theme.WeatherKotlinTheme
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.ZoneOffset

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherKotlinTheme {
                // A surface container using the 'background' color from the theme
                val lat: Double = 56.99719;
                val lon: Double = 40.97139;
                val hoursList = remember {
                    mutableStateOf(listOf<Weather>())
                }
                getGeoCoder("Ivanovo", this, hoursList)
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = Color.Blue.copy(alpha = 0.5f)
                ) {
                    Column {
                        MainPage(
                            hoursList.value.firstOrNull() ?: Weather(
                                LocalDateTime.of(
                                    1900,
                                    1,
                                    1,
                                    0,
                                    0
                                ), 0.0, 0.0, 0.0, "", "", ""
                            )
                        )
                        TabLayout(hoursList)
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
//                getWeather(lat, lon, state, context)
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
var nameCity = mutableStateOf("null")


private fun getGeoCoder(city: String, context: Context, hoursList: MutableState<List<Weather>>) {
    val url = "https://api.openweathermap.org/geo/1.0/direct?" +
            "q=${city}&" +
            "limit=1&" +
            "appid=e847b8dad5ccbf86bebe66ecdb5fdf24"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(com.android.volley.Request.Method.GET, url, { response ->
        val obj = JSONArray(response)
        val jsonObject = obj.getJSONObject(0)
        val lat = jsonObject.getDouble("lat")
        val lon = jsonObject.getDouble("lon")
        getWeather(lat, lon, context, hoursList)
        Log.d("MyLog", "response: ${response.toString()}")
        Log.d("MyTest", "lat: ${lat.toString()} lon: ${lon.toString()}")
    }, { error ->
        Log.d("MyLog", "error: ${error.toString()}")
    })
    queue.add(stringRequest)
}

private fun getWeather(
    lat: Double,
    lon: Double, /*state: MutableState<String>,*/
    context: Context, hoursList: MutableState<List<Weather>>
) {
    val url = "https://api.openweathermap.org/data/2.5/forecast?" +
            "lat=${lat}&" +
            "lon=${lon}&" +
            "lang=ru&" +
            "units=metric&" +
            "appid=e847b8dad5ccbf86bebe66ecdb5fdf24"
    nameCity.value = "test"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(com.android.volley.Request.Method.GET, url,
        { response ->

            val list = getWeatherByHour(response)
            hoursList.value = list
//            nameCity.value = obj.getJSONObject("city").getString("name")
//            val obj = Json.decodeFromString<WeatherModel>(response)
//            nameCity = obj.city.name.toString()
//            state.value = obj.list.first().weather.first().description
//            val obj = JSONObject(response)
//            state.value = obj.getJSONObject("city").getString("name")
        },
        { error ->
            Log.d("getWeather", "error: $error")
        })
    queue.add(stringRequest)
}

private fun getWeatherByHour(response: String): List<Weather> {
    if (response.isEmpty()) return listOf()
    val list = ArrayList<Weather>()
    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("city").getString("name")
    val hours = mainObject.getJSONArray("list")
    for (i in 0 until hours.length()) {
        val item = hours[i] as JSONObject
        list.add(
            Weather(
                java.time.LocalDateTime.ofEpochSecond(
                    item.getLong("dt"),
                    0,
                    ZoneOffset.UTC
                ),
                item.getJSONObject("main").getDouble("temp"),
                item.getJSONObject("main").getDouble("temp_max"),
                item.getJSONObject("main").getDouble("temp_min"),
                city,
                item.getJSONArray("weather").getJSONObject(0).getString("description"),
                item.getJSONArray("weather").getJSONObject(0).getString("icon"),
            )
        )
    }
    list[0] = list[0].copy(


    )
    return list
}
//fun initWeatherWithData() {
//    val now = java.time.LocalDateTime.now()
//    var itCurrentDay = now
//    var itNextDay = DateTime(now.year, now.month, now.day + 1, 0, 0, 0, 0, 0)
//
//    itemsToBuild.add(DayHeading(dateTime = now))
//    for (i in 0 until weatherForecast.size) {
//        val currentDateTime = weatherForecast[i].getDateTime()
//        if (currentDateTime == itNextDay) {
//            itCurrentDay = itNextDay
//            itNextDay = DateTime(
//                itNextDay.year, itNextDay.month, itNextDay.day + 1, 0, 0, 0, 0, 0
//            )
//            itemsToBuild.add(DayHeading(dateTime = itCurrentDay))
//            itemsToBuild.add(weatherForecast[i])
//        } else if (currentDateTime.isAfter(itNextDay)) {
//            itCurrentDay = itNextDay
//            itNextDay = DateTime(
//                itNextDay.year, itNextDay.month, itNextDay.day + 1, 0, 0, 0, 0, 0
//            )
//            itemsToBuild.add(DayHeading(dateTime = itCurrentDay))
//        } else {
//            itemsToBuild.add(weatherForecast[i])
//        }
//    }
//    _isLoading = false
//}
