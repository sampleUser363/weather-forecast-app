package com.example.weatherforecast.ui.screen.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.weatherforecast.R
import com.example.weatherforecast.model.City
import com.example.weatherforecast.ui.component.ConfirmDialog
import com.example.weatherforecast.ui.component.TopBar
import com.example.weatherforecast.ui.theme.WeatherForecastTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCityClick: (String) -> Unit,
) {
    var showsRequestPermissionDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION
    val finePermission = Manifest.permission.ACCESS_FINE_LOCATION
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onCityClick(City.CURRENT_LOCATION.name)
            }
        }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.home_screen_title),
                canNavigateBack = false,
                navigateUp = {}
            )
        },
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        HomeBody(
            cities = City.entries,
            onCityClick = { city ->
                if (city.isCurrentLocation) {
                    val hasCoarsePermission = ContextCompat.checkSelfPermission(context, coarsePermission) == PackageManager.PERMISSION_GRANTED
                    val hasFinePermission = ContextCompat.checkSelfPermission(context, finePermission) == PackageManager.PERMISSION_GRANTED
                    PackageManager.PERMISSION_DENIED
                    if (hasCoarsePermission || hasFinePermission) {
                        // いずれかの位置情報権限が許可されている場合は遷移処理を実行
                        onCityClick(City.CURRENT_LOCATION.name)
                    } else {
                        // 権限が付与されていない場合、権限許可依頼ダイアログを表示
                        showsRequestPermissionDialog = true
                    }
                } else {
                    onCityClick(city.name)
                }
            },
            contentPadding = innerPadding,
            modifier = modifier
        )
    }

    if (showsRequestPermissionDialog) {
        ConfirmDialog(
            titleResourceId = R.string.request_location_permission_title,
            messageResourceId = R.string.request_location_permission_message,
            onDismissTextResourceId = R.string.button_cancel,
            onDismiss = { showsRequestPermissionDialog = false },
            onConfirmTextResourceId = R.string.button_ok,
            onConfirm = {
                showsRequestPermissionDialog = false
                // MEMO: 2回拒否された場合はOS側の仕様で表示されなくなる
                requestPermissionLauncher.launch(finePermission)
            }
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.home_select_city_text),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
