package com.mevi.ui.navigation.host

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationRoute

@Composable
fun NotificationNavigationHost(
    modifier: Modifier = Modifier,
    navigationComponent: NavigationComponent
) = NavHost(
    navController = navigationComponent.getNotificationNavController(),
    startDestination = NavigationRoute.ROUTE_EMPTY.route,
    exitTransition = { ExitTransition.None },
    popExitTransition = { ExitTransition.None },
    enterTransition = { EnterTransition.None },
    popEnterTransition = { EnterTransition.None },
    contentAlignment = Alignment.TopCenter,
    modifier = modifier
) {
    composable(NavigationRoute.ROUTE_EMPTY.route) {
        Log.d("NotificationNavigationHost", "Initial empty composable")
    }
}