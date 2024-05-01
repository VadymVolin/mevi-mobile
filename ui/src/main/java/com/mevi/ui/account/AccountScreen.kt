package com.mevi.ui.account

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mevi.ui.navigation.NavigationRoute


@Composable
fun AccountScreen() {
    Text(text = NavigationRoute.ROUTE_SCREEN_ACCOUNT.route)
}