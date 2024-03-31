package com.mevi.ui.call

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mevi.ui.navigation.NavigationRoute

@Composable
fun RandomCallScreen() {
    Text(text = NavigationRoute.ROUTE_RANDOM_CALL.route)
}