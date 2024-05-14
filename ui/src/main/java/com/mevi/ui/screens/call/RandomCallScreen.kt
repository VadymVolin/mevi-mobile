package com.mevi.ui.screens.call

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mevi.ui.navigation.NavigationGraphRoute

@Composable
fun RandomCallScreen() {
    Text(text = NavigationGraphRoute.ROUTE_SCREEN_RANDOM_CALL.route)
}