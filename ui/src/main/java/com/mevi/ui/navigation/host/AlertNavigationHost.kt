package com.mevi.ui.navigation.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mevi.ui.navigation.NavigationComponent

@Composable
fun AlertNavigationHost(
    modifier: Modifier = Modifier,
    navigationComponent: NavigationComponent
) = NavHost(
    navController = navigationComponent.getAlertNavController(),
    startDestination = String(),
    modifier = modifier
) {
}