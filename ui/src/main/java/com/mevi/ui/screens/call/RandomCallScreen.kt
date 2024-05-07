package com.mevi.ui.screens.call

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mevi.ui.navigation.NavigationRoute

@Composable
fun RandomCallScreen() {
    Text(text = NavigationRoute.ROUTE_SCREEN_RANDOM_CALL.route)
}