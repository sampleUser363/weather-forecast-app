package com.example.weatherforecast.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.model.City
import com.example.weatherforecast.ui.component.TopBar
import com.example.weatherforecast.ui.theme.WeatherForecastTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCityClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.home_screen_title),
                canNavigateBack = false,
                navigateUp = {}
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        HomeBody(
            cities = City.entries,
            onCityClick = {
                onCityClick(it.name)
            },
            contentPadding = innerPadding,
            modifier = modifier
        )
    }
}

@Composable
fun HomeBody(
    cities: List<City>,
    onCityClick: (City) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = modifier.padding(contentPadding)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.home_select_city_text),
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            LazyColumn {
                itemsIndexed(cities) { index, city ->
                    CityItem(
                        city = city,
                        modifier = Modifier
                            .clickable { onCityClick(city) }
                    )
                    if (index < cities.lastIndex) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun CityItem(
    city: City,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = stringResource(city.displayNameResId),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        },
        modifier = modifier,
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}

// ======================================================
// Preview
// ======================================================
@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    WeatherForecastTheme {
        HomeBody(
            cities = City.entries,
            onCityClick = {}
        )
    }
}
