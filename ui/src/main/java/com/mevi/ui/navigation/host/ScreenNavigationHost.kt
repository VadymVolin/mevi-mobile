package com.mevi.ui.navigation.host

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mevi.ui.screens.account.AccountScreen
import com.mevi.ui.screens.call.RandomCallScreen
import com.mevi.ui.screens.chats.ChatsScreen
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationRoute

@Composable
fun ScreenNavigationHost(
    modifier: Modifier = Modifier,
    navigationComponent: NavigationComponent
) = NavHost(
    navController = navigationComponent.getScreenNavController(),
    startDestination = NavigationRoute.ROUTE_SCREEN_CHATS.route,
    exitTransition = { ExitTransition.None },
    popExitTransition = { ExitTransition.None },
    enterTransition = { EnterTransition.None },
    popEnterTransition = { EnterTransition.None },
    contentAlignment = Alignment.TopCenter,
    modifier = modifier
) {
    composable(NavigationRoute.ROUTE_SCREEN_CHATS.route) {
        ChatsScreen()
    }
    composable(NavigationRoute.ROUTE_SCREEN_RANDOM_CALL.route) {
        RandomCallScreen()
    }
    composable(NavigationRoute.ROUTE_SCREEN_ACCOUNT.route) {
        AccountScreen()
    }
}