package com.mevi.ui.navigation.host

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mevi.ui.internet.NoInternetConnectionAlert
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationRoute

@Composable
fun AlertNavigationHost(
    modifier: Modifier = Modifier,
    navigationComponent: NavigationComponent
) = NavHost(
    navController = navigationComponent.getAlertNavController(),
    startDestination = NavigationRoute.ROUTE_EMPTY.route,
    modifier = modifier
) {
    composable(NavigationRoute.ROUTE_EMPTY.route) {
        Log.d("AlertNavigationHost", "Initial empty composable")
    }
    composable(NavigationRoute.ROUTE_ALERT_NO_INTERNET.route) {
        NoInternetConnectionAlert()
    }
}