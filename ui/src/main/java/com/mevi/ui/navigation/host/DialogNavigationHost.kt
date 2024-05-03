package com.mevi.ui.navigation.host

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationRoute
import com.mevi.ui.screens.authorization.AuthorizationScreen

@Composable
fun DialogNavigationHost(
    modifier: Modifier = Modifier,
    navigationComponent: NavigationComponent
) = NavHost(
    navController = navigationComponent.getDialogNavController(),
    startDestination = NavigationRoute.ROUTE_EMPTY.route,
    modifier = modifier
) {
    composable(NavigationRoute.ROUTE_EMPTY.route) {
        Log.d("DialogNavigationHost", "Initial empty composable")
    }
    composable(NavigationRoute.ROUTE_DIALOG_AUTHORIZATION_DIALOG.route) {
        AuthorizationScreen()
    }
}