package com.mevi.ui.internet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mevi.ui.R
import com.mevi.ui.components.buttons.MeviButton
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.Route
import org.koin.compose.koinInject


const val TAG = "NoInternetConnectionAlert"

@Composable
fun NoInternetConnectionAlert(
    networkManager: NetworkManager = koinInject<NetworkManager>(),
    navigationComponent: NavigationComponent = koinInject<NavigationComponent>(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.NO_INTERNET_CONNECTION_SCREEN_TITLE),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                maxLines = 1,
                modifier = Modifier
                    .padding(16.dp, 0.dp, 16.dp, 4.dp)
                    .fillMaxWidth()
            )
            Text(
                text = stringResource(id = R.string.NO_INTERNET_CONNECTION_SCREEN_BODY),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                maxLines = 2,
                modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .fillMaxWidth()
            )
        }
        MeviButton(
            modifier = Modifier.padding(16.dp),
            onClick = {
                Log.d(TAG, "Start checking network connection")
                if (networkManager.isInternetConnectionAvailable()) {
                    Log.d(TAG, "Network is available, close the alert")
                    navigationComponent.closeScreen(Route.Dialog.ROUTE_ALERT_NO_INTERNET)
                } else {
                    Log.d(TAG, "Network is not available, keep showing the alert")
                    return@MeviButton
                }
            },
            text = stringResource(id = R.string.TRY_AGAIN_BUTTON)
        )
    }
}