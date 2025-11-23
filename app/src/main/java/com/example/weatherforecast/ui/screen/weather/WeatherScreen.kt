package com.example.weatherforecast.ui.screen.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        WeatherBody(
            uiState = viewModel.uiState,
            contentPadding = innerPadding,
            onClickRetry = { viewModel.retryWeatherInfo() }
        )
    }
}

@Composable
fun WeatherBody(
    uiState: WeatherUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClickRetry: () -> Unit,
) {
    when(uiState) {
        WeatherUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        WeatherUiState.Error -> {
            ErrorScreen(
                modifier = modifier,
                contentPadding = contentPadding,
                onClickRetry = onClickRetry
            )
        }
        is WeatherUiState.Success -> {
            WeatherForecastList(
                weatherInfoList = uiState.weatherInfos,
                modifier = modifier,
                contentPadding = contentPadding
            )
        }
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClickRetry: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.weather_forecast_error_message),
        )
        Button(
            onClick = onClickRetry,
            colors = ButtonDefaults.buttonColors(
                // 背景色
                containerColor = MaterialTheme.colorScheme.primary,
                // 文字色
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp
            ),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                stringResource(R.string.button_retry)
            )
        }
    }
}

@Composable
fun WeatherForecastList(
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
            items(weatherInfoList) { weatherInfo ->
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
fun WeatherSuccessBodyPreview() {
    val dummyData = WeatherInfo(
        weatherIconId = "04d",
        iconDescription = "曇りがち",
        temperature = 16.28,
        dt = 1763698885
    )
    val dummyList = listOf(dummyData)
    WeatherForecastTheme {
        WeatherBody(
            uiState = WeatherUiState.Success(dummyList),
            onClickRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherErrorBodyPreview() {
    WeatherForecastTheme {
        WeatherBody(
            uiState = WeatherUiState.Error,
            onClickRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherLoadingBodyPreview() {
    WeatherForecastTheme {
        WeatherBody(
            uiState = WeatherUiState.Loading,
            onClickRetry = {}
        )
    }
}
