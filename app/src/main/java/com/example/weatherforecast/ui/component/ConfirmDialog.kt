package com.example.weatherforecast.ui.component

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherforecast.R
import com.example.weatherforecast.ui.theme.WeatherForecastTheme

/** ConfirmButtonのテキストが可変なダイアログ */
@Composable
fun ConfirmDialog(
    @StringRes
    titleResourceId: Int?,
    @StringRes
    messageResourceId: Int?,
    @StringRes
    onConfirmTextResourceId: Int?,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            if (titleResourceId != null) {
                Text(stringResource(titleResourceId))
            }
        },
        text = {
            if (messageResourceId != null) {
                Text(stringResource(messageResourceId))
            }
        },
        confirmButton = {
            if (onConfirmTextResourceId != null) {
                TextButton(onClick = onConfirm) {
                    Text(stringResource(onConfirmTextResourceId))
                }
            }
        },
    )
}

/** ConfirmButtonのテキスト、DismissButtonのテキストが可変なダイアログ */
@Composable
fun ConfirmDialog(
    @StringRes
    titleResourceId: Int?,
    @StringRes
    messageResourceId: Int?,
    @StringRes
    onDismissTextResourceId: Int?,
    onDismiss: () -> Unit,
    @StringRes
    onConfirmTextResourceId: Int?,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            if (titleResourceId != null) {
                Text(stringResource(titleResourceId))
            }
        },
        text = {
            if (messageResourceId != null) {
                Text(stringResource(messageResourceId))
            }
        },
        confirmButton = {
            if (onConfirmTextResourceId != null) {
                TextButton(onClick = onConfirm) {
                    Text(stringResource(onConfirmTextResourceId))
                }
            }
        },
        dismissButton = {
            if (onDismissTextResourceId != null) {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(onDismissTextResourceId))
                }
            }
        }
    )
}

@Preview
@Composable
fun CustomConfirmDialogPreview() {
    WeatherForecastTheme {
        ConfirmDialog(
            titleResourceId = R.string.request_location_permission_title,
            messageResourceId = R.string.request_location_permission_message,
            onDismissTextResourceId = R.string.button_cancel,
            onDismiss = {},
            onConfirmTextResourceId = R.string.button_ok,
            onConfirm = {}
        )
    }
}
