package com.example.weatherforecast.ui.screen.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weatherforecast.R
import com.example.weatherforecast.model.WeatherInfo
import com.example.weatherforecast.ui.component.TopBar
import com.example.weatherforecast.ui.theme.WeatherForecastTheme

@Composable
fun WeatherScreen(
    onNavigateUp: () -> Unit,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(
                    R.string.weather_forecast_title,
                    stringResource(viewModel.cityType.displayNameResId)
                ),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = Modifier
            .fillMaxSize()
        ,
    ) { innerPadding ->
        WeatherBody(
            weatherInfoList = viewModel.dummyList,
            contentPadding = innerPadding
        )
    }
}

@Composable
fun WeatherBody(
    weatherInfoList: List<WeatherInfo>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(weatherInfoList) { index, weatherInfo ->
                WeatherItem(weatherInfo = weatherInfo)
            }
        }
    }
}

@Composable
fun WeatherItem(weatherInfo: WeatherInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(
                text = weatherInfo.dateTimeString,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(weatherInfo.iconUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = weatherInfo.iconDescription,
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f)
            )
            Text(
                text = stringResource(
                    id = R.string.expected_temperature_text,
                    formatArgs = arrayOf(weatherInfo.roundTemperature),
                ),
                fontSize = 18.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}

// ======================================================
// Preview
// ======================================================
@Preview(showBackground = true)
@Composable
fun WeatherBodyPreview() {
    val dummyData = WeatherInfo(
        iconUrl = "https://openweathermap.org/img/wn/04d@2x.png",
        iconDescription = "曇りがち",
        temperature = 16.28,
        dt = 1763698885
    )
    val dummyList = listOf(dummyData)
    WeatherForecastTheme {
        WeatherBody(weatherInfoList = dummyList)
    }
}
