package com.example.weatherforecast.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.weatherforecast.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    canNavigateBack: Boolean,
    title: String,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                WindowInsets.safeDrawing
                    .only(WindowInsetsSides.Horizontal)
                    .asPaddingValues()
            )
    ) {
        CenterAlignedTopAppBar(
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_description)
                        )
                    }
                }
            },
            modifier = modifier
        )
        HorizontalDivider()
    }
}
